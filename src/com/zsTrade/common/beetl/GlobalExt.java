package com.zsTrade.common.beetl;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.beetl.core.GroupTemplate;
import org.beetl.core.Template;
import org.beetl.ext.web.WebRenderExt;

import com.zsTrade.common.constant.ZsCatConstant;
import com.zsTrade.web.sys.utils.Symphonys;

public class GlobalExt implements WebRenderExt{

        static long version = System.currentTimeMillis();
        @Override
        public void modify(Template template, GroupTemplate arg1, HttpServletRequest arg2, HttpServletResponse arg3) {
                //js,css 的版本编号
                template.binding("sysVersion",1.1);
                template.binding("projectName", Symphonys.get("projectName"));
                template.binding("companyName", Symphonys.get("companyName"));
                template.binding("imgServer", ZsCatConstant.IMGSERVER);
                
        }

}