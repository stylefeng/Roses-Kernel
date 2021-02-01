package cn.stylefeng.roses.kernel.monitor.system.holder;

import cn.stylefeng.roses.kernel.monitor.system.SystemHardwareCalculator;
import cn.stylefeng.roses.kernel.timer.api.TimerAction;

/**
 * 定时刷新服务器状态信息
 *
 * @author fengshuonan
 * @date 2021/1/31 21:52
 */
public class SystemHardwareInfoHolder implements TimerAction {

    private SystemHardwareCalculator systemHardwareCalculator = null;

    @Override
    public void action() {
        SystemHardwareCalculator newInfo = new SystemHardwareCalculator();
        newInfo.calc();
        systemHardwareCalculator = newInfo;
    }

    public SystemHardwareCalculator getSystemHardwareInfo() {
        if (systemHardwareCalculator != null) {
            return systemHardwareCalculator;
        }

        systemHardwareCalculator = new SystemHardwareCalculator();
        systemHardwareCalculator.calc();
        return systemHardwareCalculator;
    }

}
