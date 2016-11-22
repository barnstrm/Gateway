package com.cognetyx.Gateway.filters.pre;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.util.HTTPRequestUtils;
import com.netflix.zuul.ZuulFilter;

public class SimpleFilter extends ZuulFilter {

  @Override
  public String filterType() {
    return "pre";
  }

  @Override
  public int filterOrder() {
    return 1;
  }

  @Override
  public boolean shouldFilter() {
    return true;
  }

  @Override
  public Object run() {
    RequestContext ctx = RequestContext.getCurrentContext();
    HttpServletRequest request = ctx.getRequest();
    ctx.setDebugRouting(true);
    ctx.setDebugRequest(true);

    Map<String, String[]> qParams = request.getParameterMap();
    if( qParams != null )
    {
        StringBuilder sb = new StringBuilder("Params: ");
        for(Map.Entry<String, String[]> entry : qParams.entrySet())
        {
        	String theKey = entry.getKey();
        	List<String> theValues = new ArrayList<String>();
        	sb.append(theKey + ": {");
        	for(int i = 0; i < entry.getValue().length; i++) {
        		theValues.add(entry.getValue()[i]);
        		if( i + 1 == entry.getValue().length)
        			sb.append(entry.getValue()[i] + "}");
        		else
        			sb.append(entry.getValue()[i] + ", ");
        	}
        	HTTPRequestUtils.getInstance().getQueryParams().put(theKey, theValues);
        	sb.append(", ");
        }

        System.out.println(sb.toString());    	
    }
    System.out.println(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));

    return null;
  }

}