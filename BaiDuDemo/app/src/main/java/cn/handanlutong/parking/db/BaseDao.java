package cn.handanlutong.parking.db;

import java.util.List;

public abstract class BaseDao<T> {

    /**
     * 添加一条记录
     * @param t
     */
    public abstract void addOne(T t);

    /**
     * 添加所有记录
     * @param lists
     */
    public abstract void addAll(List<T> lists);

    /**
     * 查询所有记录
     * @return
     */
    public abstract List<T> queryAll();
    /**
     * 清空所有记录
     * @return
     */
    public abstract void deleteAll();

}
