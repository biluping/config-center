package org.rabbit.flow;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.ReflectUtil;

import java.util.*;
import java.util.function.BiConsumer;

/**
 * 规则引擎
 */
public class FlowExecutor {

    private static final Map<String, List<Object>> chain = new HashMap<>();

    /**
     * 添加流程，组件无序
     * @param flowName 流程名称
     * @param interfaceClass 组件接口名称
     */
    public static void addFlow(String flowName, Class<?> interfaceClass){
        // 组件扫描
        Set<Class<?>> classes = ClassUtil.scanPackage(ClassUtil.getPackage(interfaceClass),
                c -> ObjectUtil.notEqual(interfaceClass, c) && interfaceClass.isAssignableFrom(c));

        // 添加到流程中
        addFlow(flowName, classes);
    }

    /**
     * 添加流程，组件有序
     * @param flowName 流程名称
     * @param cmpClass 组件类型数组，按顺序排
     */
    public static void addFlow(String flowName, Collection<Class<?>> cmpClass){
        // 组件实例化、添加到流程
        Assert.notEmpty(cmpClass, "流程组件不能为空, flowName:{}", flowName);
        Assert.isFalse(chain.containsKey(flowName), "流程已存在, flowName:{}", flowName);
        ArrayList<Object> list = new ArrayList<>();
        for (Class<?> c : cmpClass) {
            list.add(ReflectUtil.newInstance(c));
        }
        chain.put(flowName, list);
    }

    /**
     * 执行流程
     * @param flowName 流程名称
     * @param context 上下文类
     * @param parameter 参数
     */
    @SuppressWarnings("unchecked")
    public static  <T,E> T execute(String flowName, T context, E parameter){
        List<Object> list = chain.get(flowName);
        Assert.notEmpty(list, "流程不存在:{}", flowName);
        for (Object o : list) {
            ((BiConsumer<T, E>) o).accept(context, parameter);
        }
        return context;
    }
}
