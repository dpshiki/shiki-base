package cn.iocoder.yudao.module.system.framework.log;

import cn.iocoder.yudao.framework.common.util.json.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: BizLogger
 * @Description: TODO
 * @Author: jialiangteng
 * @Date: 2024/11/13 19:28
 **/
public class BizLogger {
    /**
     * 对应logback配置的业务监控日志输出
     */
    private static Logger log = LoggerFactory.getLogger("BUSI.MONITOR");

    /**
     * 接口监控记录
     *
     * @param apiName 接口名称
     * @param methodName 方法名称
     * @param costTime 花费时间（毫秒）
     * @param respCode 返回码
     * @param respMsg 返回信息
     */
    public static void logApiEvent(String apiName, String methodName, Long costTime, String respCode, String respMsg) {
        logApiEvent(apiName, methodName, costTime, respCode, respMsg, null);
    }

    /**
     * 接口监控记录
     *
     * @param apiName 接口名称
     * @param methodName 方法名称
     * @param costTime 花费时间（毫秒）
     * @param respCode 返回码
     * @param respMsg 返回信息
     * @param params 扩展信息传入（不要将以上的入参字段作为key，以及ip，monitorType）
     */
    public static void logApiEvent(String apiName, String methodName, Long costTime, String respCode, String respMsg,
                                   Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("monitorType", "MonitorTypeEnum.INTERFACE.name()");
        params.put("apiName", apiName);
        params.put("methodName", methodName);
        params.put("costTime", costTime);
        params.put("respCode", respCode);
        params.put("respMsg", respMsg);
        params.put("traceId", MDC.get("traceId"));
        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }

    /**
     * 接口监控记录
     *
     * @param apiName 接口名称
     * @param methodName 方法名称
     * @param costTime 花费时间（毫秒）
     * @param respCode 返回码
     * @param respMsg 返回信息
     * @param params 扩展信息传入（不要将以上的入参字段作为key，以及ip，monitorType）
     */
    public static void logCallBackEvent(String apiName, String methodName, Long costTime, String respCode, String respMsg,
                                        Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("monitorType", "MonitorTypeEnum.CALLBACK.name()");
        params.put("apiName", apiName);
        params.put("methodName", methodName);
        params.put("costTime", costTime);
        params.put("respCode", respCode);
        params.put("respMsg", respMsg);
        params.put("traceId", MDC.get("traceId"));
        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }

    private static void removeSensitiveField(Map<String, Object> params) {
        if (params != null) {
            if (params.containsKey("ip")) {
                params.remove("ip");
            }
            if (params.containsKey("appName")) {
                params.remove("appName");
            }
            if (params.containsKey("hostName")) {
                params.remove("hostName");
            }
            if (params.containsKey("agentType")) {
                params.remove("agentType");
            }
            if (params.containsKey("source")) {
                params.remove("source");
            }
            for (String key : params.keySet()) {
                if (params.get(key) instanceof Date) {
//                    params.put(key,
//                            DateUtil.formatDateByPattern((Date) params.get(key), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
                }

            }
        }
    }

    /**
     * 业务监控记录
     *
     * @param bizType 业务类型
     * @param busiScene 业务场景
     * @param busiTime 业务时间
     * @param busiStatus 业务状态
     * @param busiStatusDesc 业务状态描述
     * @param errcode 错误编码
     * @param errMsg 错误信息
     */
    public static void logBusiEvent(String bizType, String busiScene, Date busiTime, String busiStatus,
                                    String busiStatusDesc, String errcode, String errMsg) {
        logBusiEvent(bizType, busiScene, busiTime, busiStatus, busiStatusDesc, errcode, errMsg, null);
    }

    /**
     * 业务监控记录
     *
     * @param bizType 业务类型
     * @param busiScene 业务场景
     * @param busiTime 业务时间
     * @param busiStatus 业务状态
     * @param busiStatusDesc 业务状态描述
     * @param errcode 错误编码
     * @param errMsg 错误信息
     * @param params 扩展信息
     */
    public static void logBusiEvent(String bizType, String busiScene, Date busiTime, String busiStatus,
                                    String busiStatusDesc, String errcode, String errMsg, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }
        params.put("monitorType", "MonitorTypeEnum.BUSI.name()");
        params.put("bizType", bizType);
        params.put("busiScene", busiScene);
        params.put("busiTime", busiTime);
        params.put("busiStatus", busiStatus);
        params.put("busiStatusDesc", busiStatusDesc);
        params.put("errcode", errcode);
        params.put("errMsg", errMsg);
        params.put("traceId", MDC.get("traceId"));

        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }

    /**
     * ons日志记录
     *
     * @param topic
     * @param costTime
     * @param startDeliverTime
     * @param reconsumeTimes
     * @param msgId
     * @param actionResult
     */
    public static void logOnsEvent(String topic, Long costTime, Date startDeliverTime, Integer reconsumeTimes,
                                   String msgId, String actionResult) {
        logOnsEvent(topic, costTime, startDeliverTime, reconsumeTimes, msgId, actionResult, null);
    }


    /**
     * mq日志记录
     *
     * @param topic
     * @param costTime
     * @param startDeliverTime
     * @param reconsumeTimes
     * @param msgId
     * @param actionResult
     * @param params
     */
    public static void logOnsEvent(String topic, Long costTime, Date startDeliverTime, Integer reconsumeTimes,
                                   String msgId, String actionResult, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        params.put("monitorType", "MonitorTypeEnum.MQ.name()");
        params.put("topic", topic);
        params.put("costTime", costTime);
        params.put("startDeliverTime", startDeliverTime);
        params.put("reconsumeTimes", reconsumeTimes);
        params.put("msgId", msgId);
        params.put("traceId", MDC.get("traceId"));
        params.put("actionResult", actionResult);
        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }

    /**
     * 定时task日志记录
     *
     * @param taskName
     * @param costTime
     * @param exception
     * @param success
     */
    public static void logTaskEvent(String taskName, Long costTime, String exception, Boolean success, Integer scount,
                                    Integer fcount) {
        logTaskEvent(taskName, costTime, exception, success, scount, fcount, null);
    }

    /**
     * 定时task日志记录
     *
     * @param taskName
     * @param costTime
     * @param exception
     * @param success
     * @param params
     */
    public static void logTaskEvent(String taskName, Long costTime, String exception, Boolean success, Integer scount,
                                    Integer fcount, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        params.put("monitorType", "MonitorTypeEnum.TASK.name()");
        params.put("taskName", taskName);
        params.put("costTime", costTime);
        params.put("exception", exception);
        params.put("success", success);
        params.put("scount", scount);
        params.put("fcount", fcount);
        params.put("traceId", MDC.get("traceId"));
        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }

    /**
     * 外部调用监控
     *
     * @param serviceName
     * @param methodName
     * @param costTime
     * @param respCode
     * @param success
     */
    public static void logRpcEvent(String serviceName, String methodName, Long costTime, String respCode,
                                   Boolean success) {
        logRpcEvent(serviceName, methodName, costTime, respCode, success, null);
    }

    /**
     * 外部调用监控
     *
     * @param serviceName
     * @param methodName
     * @param costTime
     * @param respCode
     * @param success
     * @param params
     */
    public static void logRpcEvent(String serviceName, String methodName, Long costTime, String respCode,
                                   Boolean success, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        params.put("monitorType", "MonitorTypeEnum.RPC.name()");
        params.put("serviceName", serviceName);
        params.put("methodName", methodName);
        params.put("costTime", costTime);
        params.put("success", success);
        params.put("respCode", respCode);
        params.put("traceId", MDC.get("traceId"));
        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }

    /**
     * 线程任务Busi日志
     *
     * @param taskName
     * @param costTime
     * @param exception
     * @param success
     */
    public static void logThreadEvent(String taskName, String methodName, Long costTime, String exception,
                                      Boolean success) {
        logThreadEvent(taskName, methodName, costTime, exception, success, null);
    }

    /**
     * 线程任务Busi日志
     *
     * @param taskName
     * @param costTime
     * @param exception
     * @param success
     * @param params
     */
    public static void logThreadEvent(String taskName, String methodName, Long costTime, String exception,
                                      Boolean success, Map<String, Object> params) {
        if (params == null) {
            params = new HashMap<String, Object>();
        }

        params.put("monitorType", "MonitorTypeEnum.THREADTASK.name()");
        params.put("taskName", taskName);
        params.put("methodName", methodName);
        params.put("costTime", costTime);
        params.put("exception", exception);
        params.put("success", success);
        params.put("traceId", MDC.get("traceId"));
        // params.put("time", DateUtil.formatDateByPattern(new Date(), "yyyy-MM-dd'T'HH:mm:ss.sss'+08:00'"));
        removeSensitiveField(params);
        log.info(JsonUtils.toJsonString(params));
    }
}
