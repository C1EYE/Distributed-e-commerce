package com.c1eye.dsmail.product.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.c1eye.dsmail.product.service.CategoryBrandRelationService;
import com.c1eye.dsmail.product.vo.Catelog2Vo;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.c1eye.common.utils.PageUtils;
import com.c1eye.common.utils.Query;

import com.c1eye.dsmail.product.dao.CategoryDao;
import com.c1eye.dsmail.product.entity.CategoryEntity;
import com.c1eye.dsmail.product.service.CategoryService;
import org.springframework.transaction.annotation.Transactional;


@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryDao, CategoryEntity> implements CategoryService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private CategoryBrandRelationService categoryBrandRelationService;

    @Autowired
    private RedissonClient redisson;


    @Override

    public PageUtils queryPage(Map<String, Object> params) {
        IPage<CategoryEntity> page = this.page(
                new Query<CategoryEntity>().getPage(params),
                new QueryWrapper<CategoryEntity>()
                                              );

        return new PageUtils(page);
    }

    @Override
    public List<CategoryEntity> listWithTree() {
        // 查出所有分类
        List<CategoryEntity> list = baseMapper.selectList(null);
        return list;

    }

    @Override
    public void removeMenuByIds(List<Long> asList) {
        // TODO 检查当前删除菜单是否被引用

        baseMapper.deleteBatchIds(asList);
    }


    @Override
    public Long[] findCatelogPath(Long catelogId) {
        List<Long> paths = new ArrayList<>();
        CategoryEntity byId = this.getById(catelogId);
        if (byId.getParentCid() != 0) {
            findParentPath(catelogId, paths);
        }
        return paths.toArray(new Long[paths.size()]);
    }

    /**
     * 级联更新
     *
     * @param category
     */
    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "category", key = "'getLevel1Categorys'"),
            @CacheEvict(value = "category", key = "'getCatalogJson'")
    })
    public void updateCascade(CategoryEntity category) {
        this.updateById(category);
        categoryBrandRelationService.updateCategory(category.getCatId(), category.getName());
    }

    @Cacheable(value = {"category"}, key = "#root.methodName",sync = true)
    @Override
    public List<CategoryEntity> getLevelOneCategorys() {
        List<CategoryEntity> list = baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq("parent_cid", 0));
        return list;
    }

    public Map<String, List<Catelog2Vo>> getCatalogJson2() {
        // 查询缓存
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            // 缓存未命中，查询数据库
            Map<String, List<Catelog2Vo>> catalogJsonFromDB = getCatalogJsonFromDBWithRedisLock();
            return catalogJsonFromDB;
        }
        // 缓存命中，直接返回
        return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
    }

    @Override
    @Cacheable(value = "category", key = "#root.methodName")
    public Map<String, List<Catelog2Vo>> getCatalogJson() {
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            List<CategoryEntity> allCategory = baseMapper.selectList(null);

            // 一级分类
            List<CategoryEntity> levelOneCategorys = getByParentCid(allCategory, 0L);
            // 封装数据
            Map<String, List<Catelog2Vo>> res =
                    levelOneCategorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString()
                            , v -> {
                                // 二级分类
                                List<CategoryEntity> categoryEntities =
                                        getByParentCid(allCategory, v.getCatId());
                                List<Catelog2Vo> catelog2Vos = null;
                                if (categoryEntities != null) {
                                    catelog2Vos = categoryEntities.stream().map(l2 -> {
                                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null,
                                                l2.getCatId().toString(), l2.getName());
                                        // 三级分类
                                        List<CategoryEntity> level3Catelog =
                                                getByParentCid(allCategory, l2.getCatId());
                                        if (level3Catelog != null) {
                                            List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                                                Catelog2Vo.Catelog3Vo catelog3Vo =
                                                        new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(),
                                                                l3.getCatId().toString(), l3.getName());
                                                return catelog3Vo;
                                            }).collect(Collectors.toList());
                                            catelog2Vo.setCatalog3List(collect);
                                        }
                                        return catelog2Vo;
                                    }).collect(Collectors.toList());

                                }
                                return catelog2Vos;
                            }));
            return res;
        } else {
            return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        }
    }


    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithRedisLock() {
        RLock lock = redisson.getLock("CatalogJsonLock");
        lock.lock();
        Map<String, List<Catelog2Vo>> catalogMap = null;
        try {
            catalogMap = getCatalogMap();
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }

//            String lockValue = redisTemplate.opsForValue().get("lock");
//            if(lockValue .equals(uuid)){
//                redisTemplate.delete("lock");
//            }
//            String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else " +
//                    "return 0 end";
//            redisTemplate.execute(new DefaultRedisScript<Integer>(script, Integer.class), Arrays.asList("lock"),
//            uuid);
        return catalogMap;

    }


    public Map<String, List<Catelog2Vo>> getCatalogJsonFromDBWithLocalLock() {
        synchronized (this) {
            return getCatalogMap();
        }
    }

    private Map<String, List<Catelog2Vo>> getCatalogMap() {
        // 双检查
        String catalogJSON = redisTemplate.opsForValue().get("catalogJSON");
        if (StringUtils.isEmpty(catalogJSON)) {
            List<CategoryEntity> allCategory = baseMapper.selectList(null);

            // 一级分类
            List<CategoryEntity> levelOneCategorys = getByParentCid(allCategory, 0L);
            // 封装数据
            Map<String, List<Catelog2Vo>> res =
                    levelOneCategorys.stream().collect(Collectors.toMap(k -> k.getCatId().toString()
                            , v -> {
                                // 二级分类
                                List<CategoryEntity> categoryEntities =
                                        getByParentCid(allCategory, v.getCatId());
                                List<Catelog2Vo> catelog2Vos = null;
                                if (categoryEntities != null) {
                                    catelog2Vos = categoryEntities.stream().map(l2 -> {
                                        Catelog2Vo catelog2Vo = new Catelog2Vo(v.getCatId().toString(), null,
                                                l2.getCatId().toString(), l2.getName());
                                        // 三级分类
                                        List<CategoryEntity> level3Catelog =
                                                getByParentCid(allCategory, l2.getCatId());
                                        if (level3Catelog != null) {
                                            List<Catelog2Vo.Catelog3Vo> collect = level3Catelog.stream().map(l3 -> {
                                                Catelog2Vo.Catelog3Vo catelog3Vo =
                                                        new Catelog2Vo.Catelog3Vo(l2.getCatId().toString(),
                                                                l3.getCatId().toString(), l3.getName());
                                                return catelog3Vo;
                                            }).collect(Collectors.toList());
                                            catelog2Vo.setCatalog3List(collect);
                                        }
                                        return catelog2Vo;
                                    }).collect(Collectors.toList());

                                }
                                return catelog2Vos;
                            }));
            redisTemplate.opsForValue().set("catalogJSON", JSON.toJSONString(res));
            return res;
        } else {
            return JSON.parseObject(catalogJSON, new TypeReference<Map<String, List<Catelog2Vo>>>() {});
        }
    }


    private List<CategoryEntity> getByParentCid(List<CategoryEntity> selectList, Long parentCid) {
//        return baseMapper.selectList(new QueryWrapper<CategoryEntity>().eq(
//                "parent_cid", v.getCatId()));
        return selectList.stream().filter(item -> item.getParentCid().equals(parentCid)).collect(Collectors.toList());
    }

    private List<Long> findParentPath(Long cid, List<Long> path) {
        CategoryEntity self = this.getById(cid);
        if (self.getParentCid() != 0) {
            findParentPath(self.getParentCid(), path);
        }
        path.add(self.getCatId());
        return path;
    }


}