package com.ruoyi.system.domain.vo;

import lombok.Data;

/**
 * 路由显示信息
 *
 * @author ruoyi
 */
@Data
public class MetaVo {
    /**
     * 显示状态（0显示 1隐藏）
     */
    private String visible;
    /**
     * 设置该路由在侧边栏和面包屑中展示的名字
     */
    private String title;

    /**
     * 设置该路由的图标，对应路径src/icons/svg
     */
    private String icon;

    public MetaVo() {
    }

    public MetaVo(String title, String icon,String visible) {
        this.title = title;
        this.icon = icon;
        this.visible=visible;
    }


}
