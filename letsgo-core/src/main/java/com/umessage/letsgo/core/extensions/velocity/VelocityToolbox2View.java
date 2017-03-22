package com.umessage.letsgo.core.extensions.velocity;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.context.Context;
import org.apache.velocity.tools.Scope;
import org.apache.velocity.tools.ToolboxFactory;
import org.apache.velocity.tools.view.VelocityView;
import org.apache.velocity.tools.view.ViewToolContext;
import org.springframework.web.servlet.view.velocity.VelocityLayoutView;

public class VelocityToolbox2View extends VelocityLayoutView {
    @Override
    protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ViewToolContext ctx = new ViewToolContext(getVelocityEngine(), request, response, getServletContext());
        ctx.putAll(model);
        
        VelocityView toolsManager = new VelocityView(getServletContext());
        
        toolsManager.setVelocityEngine(getVelocityEngine());
        toolsManager.configure(getServletContext().getRealPath(getToolboxConfigLocation()));
        
        ToolboxFactory toolboxFactory = toolsManager.getToolboxFactory();
        if (toolboxFactory.hasTools(Scope.REQUEST)) {
            ctx.addToolbox(toolboxFactory.createToolbox(Scope.REQUEST));
        }
        if (toolboxFactory.hasTools(Scope.APPLICATION)) {
            ctx.addToolbox(toolboxFactory.createToolbox(Scope.APPLICATION));
        }
        if (toolboxFactory.hasTools(Scope.SESSION)) {
            ctx.addToolbox(toolboxFactory.createToolbox(Scope.SESSION));
        }

        return ctx;
    }
}