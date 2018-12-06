package com.banry.pscm.account;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSONObject;
import com.banry.pscm.persist.dds.DynamicDataSourceConfiguration;
import com.banry.pscm.persist.dds.DynamicDataSourceContextHolder;
import com.banry.pscm.service.account.AccountException;
import com.banry.pscm.service.account.SysUsers;
import com.banry.pscm.service.account.SysUsersKey;
import com.banry.pscm.service.account.SysUsersService;
import com.banry.pscm.service.account.Tenant;
import com.banry.pscm.service.tender.Supplier;
import com.banry.pscm.service.tender.SupplierService;
import com.banry.pscm.service.tender.TenderPlanException;

/**
 * @author Xu Dingkui
 * @date 2017年6月26日
 */
public class DisUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {  
    //~ Static fields/initializers =====================================================================================  
	
	
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "username";  
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";  
    public static final String SPRING_SECURITY_FORM_TENANTCODE_KEY = "tenantaccount";
    public static final String SPRING_SECURITY_FORM_IS_SUPPLIER = "isSupplier";
    public static final String SPRING_SECURITY_FORM_TENDER_PLAN_CODE = "tenderPlanCode";
    public static final String USERNAME_LOGINID_SPLIT = "/";
  
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;  
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;  
    private String tenantaccount = SPRING_SECURITY_FORM_TENANTCODE_KEY;
    private String isSupplier = SPRING_SECURITY_FORM_IS_SUPPLIER;
    private String tenderPlanCode = SPRING_SECURITY_FORM_TENDER_PLAN_CODE;
    
    //定义移动端请求的所有可能类型
    private final static String[] agent = { "Android", "iPhone", "iPod","iPad", "Windows Phone", "MQQBrowser", "PostmanRuntime" }; 
    
    private boolean postOnly = true;  
//    @Autowired
//    private SysResourceService resourceService;
	private SysUsersService sysUsersService;
	
	private SupplierService supplierService;
	
	private RestTemplate restTemplate;
	
	private Environment ev;	
  
    //~ Constructors ===================================================================================================  
  
    public DisUsernamePasswordAuthenticationFilter() {  
    	super(new AntPathRequestMatcher("/login", "POST"));
    }  
  
    //~ Methods ========================================================================================================  
  
    public SysUsersService getSysUsersService() {
		return sysUsersService;
	}

	public void setSysUsersService(SysUsersService sysUsersService) {
		this.sysUsersService = sysUsersService;
	}

	public SupplierService getSupplierService() {
		return supplierService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Environment getEv() {
		return ev;
	}

	public void setEv(Environment ev) {
		this.ev = ev;
	}

	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {  
        if (postOnly && !request.getMethod().equals("POST")) {  
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());  
        }  
  
        String username = obtainUsername(request);  
        String password = obtainPassword(request);  
        String tenantaccount = obtainTenantaccount(request);
        String isSupplier = obtainIsSupplier(request);
        String tenderPlanCode = obtainTenderPlanCode(request);
        String tenantCode = "";
          
        if (username == null) {  
            username = "";  
        }  
  
        if (password == null) {  
            password = "";  
        }  
          
        if (tenantaccount == null) {  
            tenantaccount = "";  
        }
        
        username = username.trim();  
        password = password.trim();  
        tenantaccount = tenantaccount.trim();  
        //判断租户是否存在
        if (!DynamicDataSourceConfiguration.dataSourceMap.containsKey(tenantaccount)) {
        	throw new CredentialsExpiredException("Tenant is not exists!");
        } else {
        	//获取租户列表信息
        	JSONObject tenant = restTemplate
    				.getForEntity(ev.getProperty("pscm.tenant.rest.url") + "/tenant/findTenantByAccount?account=" + tenantaccount, JSONObject.class)
    				.getBody();
        	if (tenant != null) {
        		String parentTenantAccount = "";
        		//租户类型
				String tenantType = "";
        		List<Tenant> tenantLst = new ArrayList<Tenant>();	
    				String tc = tenant.get("account").toString();
				if (tenantaccount.equals(tc)) {
					//租户类型
					tenantType = tenant.get("tenantType").toString();
					//租户编号
					tenantCode = tenant.get("tenantCode").toString();
					SessionUtil.setSessionValue(request, SessionUtil.TENANT_TYPE, tenantType);
					//用户编号
					SessionUtil.setSessionValue(request, SessionUtil.USER_CODE, username);
					//用户当前租户
					SessionUtil.setSessionValue(request, SessionUtil.CURRENT_TENANT_ACCOUNT, tenantaccount);
					//用户当前租户编号
					SessionUtil.setSessionValue(request, SessionUtil.CURRENT_TENANT_CODE, tenantCode);
					//是否供方用户
					SessionUtil.setSessionValue(request, SessionUtil.IS_SUPPLIER, isSupplier);
					//招标计划编码
					SessionUtil.setSessionValue(request, SessionUtil.TENDER_PLAN_CODE, tenderPlanCode);
					//项目级租户
					if ("0".equals(tenantType)) {
						parentTenantAccount = tenant.get("parentAccount").toString();
						SessionUtil.setSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT, parentTenantAccount);
						SessionUtil.setSessionValue(request, SessionUtil.TENANT_ACCOUNT, tenantaccount);
						//关联实体
						JSONObject tenantReal = (JSONObject) tenant.getJSONObject("tenantReal");
						if (tenantReal != null) {
							//项目部名称
							SessionUtil.setSessionValue(request, SessionUtil.PROJECT_DEPARTMENT_NAME, tenantReal.get("name").toString());
						} else {
							SessionUtil.setSessionValue(request, SessionUtil.PROJECT_DEPARTMENT_NAME, "");
						}
						//父租户
						JSONObject parentTenant = restTemplate
			    				.getForEntity(ev.getProperty("pscm.tenant.rest.url") + "/tenant/findTenantByAccount?account=" + parentTenantAccount, JSONObject.class)
			    				.getBody();
						//父租户关联实体
						JSONObject parentTenantReal = (JSONObject) parentTenant.getJSONObject("tenantReal");
						if (parentTenantReal != null) {
							//公司名称
							SessionUtil.setSessionValue(request, SessionUtil.COMPANY_NAME, parentTenantReal.get("name").toString());
						} else {
							SessionUtil.setSessionValue(request, SessionUtil.COMPANY_NAME, "");
						}
			        	DynamicDataSourceContextHolder.set(parentTenantAccount);
					//公司级租户
					} else if ("1".equals(tenantType)) {
						parentTenantAccount = tenantaccount;
						SessionUtil.setSessionValue(request, SessionUtil.COMPANY_LEVEL_TENANT_ACCOUNT, parentTenantAccount);
			        	DynamicDataSourceContextHolder.set(tenantaccount);
//			        	tenantLst = (ArrayList)tenant.get("sonTenant");
//			        	request.getSession().setAttribute("TENANT_LIST", tenantLst);
					//集团级租户
					} else if ("2".equals(tenantType)) {
						
					}
				}
        	}
        	
        }
        //将登录用户名与租户code 是否供应商使用 / 连接起来
        username = username + USERNAME_LOGINID_SPLIT + tenantCode + USERNAME_LOGINID_SPLIT + tenantaccount
        		+ USERNAME_LOGINID_SPLIT + isSupplier;     
  
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);  
  
        // Allow subclasses to set the "details" property  
        setDetails(request, authRequest);  
        
        return this.getAuthenticationManager().authenticate(authRequest);  
    }  
  
    /**  
     * Enables subclasses to override the composition of the password, such as by including additional values  
     * and a separator.<p>This might be used for example if a postcode/zipcode was required in addition to the  
     * password. A delimiter such as a pipe (|) should be used to separate the password and extended value(s). The  
     * <code>AuthenticationDao</code> will need to generate the expected password in a corresponding manner.</p>  
     *  
     * @param request so that request attributes can be retrieved  
     *  
     * @return the password that will be presented in the <code>Authentication</code> request token to the  
     *         <code>AuthenticationManager</code>  
     */  
    protected String obtainPassword(HttpServletRequest request) {  
        return request.getParameter(passwordParameter);  
    }  
      
    //获取租户
    protected String obtainTenantaccount(HttpServletRequest request){  
           return request.getParameter(tenantaccount);  
    }  
      
    //获取是否供方
    protected String obtainIsSupplier(HttpServletRequest request){  
           return request.getParameter(isSupplier) != null ? request.getParameter(isSupplier) : "N";  
    }
    
    //获取招标计划编码
    protected String obtainTenderPlanCode(HttpServletRequest request){  
	       return request.getParameter(tenderPlanCode) != null ? request.getParameter(tenderPlanCode) : "";  
	}
      
    /**  
     * Enables subclasses to override the composition of the username, such as by including additional values  
     * and a separator.  
     *  
     * @param request so that request attributes can be retrieved  
     *  
     * @return the username that will be presented in the <code>Authentication</code> request token to the  
     *         <code>AuthenticationManager</code>  
     */  
    protected String obtainUsername(HttpServletRequest request) {  
        return request.getParameter(usernameParameter);  
    }  
  
    /**  
     * Provided so that subclasses may configure what is put into the authentication request's details  
     * property.  
     *  
     * @param request that an authentication request is being created for  
     * @param authRequest the authentication request object that should have its details set  
     */  
    protected void setDetails(HttpServletRequest request, UsernamePasswordAuthenticationToken authRequest) {  
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));  
    }  
  
    /**  
     * Sets the parameter name which will be used to obtain the username from the login request.  
     *  
     * @param usernameParameter the parameter name. Defaults to "j_username".  
     */  
    public void setUsernameParameter(String usernameParameter) {  
        Assert.hasText(usernameParameter, "Username parameter must not be empty or null");  
        this.usernameParameter = usernameParameter;  
    }  
  
    /**  
     * Sets the parameter name which will be used to obtain the password from the login request..  
     *  
     * @param passwordParameter the parameter name. Defaults to "j_password".  
     */  
    public void setPasswordParameter(String passwordParameter) {  
        Assert.hasText(passwordParameter, "Password parameter must not be empty or null");  
        this.passwordParameter = passwordParameter;  
    }  
  
    /**  
     * Defines whether only HTTP POST requests will be allowed by this filter.  
     * If set to true, and an authentication request is received which is not a POST request, an exception will  
     * be raised immediately and authentication will not be attempted. The <tt>unsuccessfulAuthentication()</tt> method  
     * will be called as if handling a failed authentication.  
     * <p>  
     * Defaults to <tt>true</tt> but may be overridden by subclasses.  
     */  
    public void setPostOnly(boolean postOnly) {  
        this.postOnly = postOnly;  
    }  
  
    public final String getUsernameParameter() {  
        return usernameParameter;  
    }  
  
    public final String getPasswordParameter() {  
        return passwordParameter;  
    }

	/**
	 * @return the tenantaccount
	 */
	public String getTenantaccount() {
		return tenantaccount;
	}

	/**
	 * @param tenantaccount the tenantaccount to set
	 */
	public void setTenantaccount(String tenantaccount) {
		this.tenantaccount = tenantaccount;
	}  
	
	public String getIsSupplier() {
		return isSupplier;
	}

	public void setIsSupplier(String isSupplier) {
		this.isSupplier = isSupplier;
	}

	public String getTenderPlanCode() {
		return tenderPlanCode;
	}

	public void setTenderPlanCode(String tenderPlanCode) {
		this.tenderPlanCode = tenderPlanCode;
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
			System.out.println("successfulAuthentication called");
			if (checkAgentIsMobile(request.getHeader("User-Agent"))) {
				SavedRequestAwareAuthenticationSuccessHandler srh = new SavedRequestAwareAuthenticationSuccessHandler();
		        this.setAuthenticationSuccessHandler(srh);
		        srh.setRedirectStrategy(new RedirectStrategy() {
		            @Override
		            public void sendRedirect(HttpServletRequest httpServletRequest,
		                    HttpServletResponse httpServletResponse, String s) throws IOException {
		                    //do nothing, no redirect
		            }
		        });
		        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
		        Writer out = responseWrapper.getWriter();
		        String username = obtainUsername(request);
		        String tenantaccount = obtainTenantaccount(request);
		        String isSupplier = obtainIsSupplier(request);
		        String truename = "";
		        String roleName = "";
		        if ("Y".equals(isSupplier)) {
		        	try {
						Supplier sp = supplierService.selectByUserCodeOrTel(username);
						if (sp != null) {
							username = sp.getUserCode();
							truename = sp.getSupplierName();
							//供方编号
							SessionUtil.setSessionValue(request, SessionUtil.SUPPLIER_CREDIT_CODE, sp.getSupplierCreditCode());
							//供方名称
							SessionUtil.setSessionValue(request, SessionUtil.SUPPLIER_NAME, truename);
						}
					} catch (TenderPlanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        } else {
			        try {
			        	SysUsersKey key = new SysUsersKey(username, tenantaccount);
				        SysUsers users = (SysUsers)sysUsersService.getByCode(key);
						if (users == null) {
							users = (SysUsers)sysUsersService.getByTelephone(username);
						}
						if (users != null) {
							username = users.getUserCode();
							truename = users.getUserName();
							roleName = users.getPositionCode();
						}
			        } catch (AccountException e) {
			        	e.printStackTrace();
			        }
		        }
		        String menujson = "";
//		        try {
//					List<SysResource> resLst = resourceService.findSysResourceByUserCode(username, "A000", "2");
//					menujson = JSON.toJSONString(resLst);
//				} catch (AccountException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				try {
//					List<Role> roleLst = rolesService.findRoleByUser(username);
//					for (Role r : roleLst) {
//						if ("".equals(roleName)) {
//							roleName = r.getName();
//						} else {
//							roleName += ">" + r.getName();
//						}
//					}
//				} catch (AccountException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
		        out.write("{\"status\":0, \"userCode\":\"" + username + "\",\"userName\":\"" + truename + "\",\"roleName\":\"" + roleName + "\",\"message\":\"成功\",\"sessionid\":\""+request.getSession().getId()+"\", \"menu\":" + menujson + "}");
		       
		        out.close();
			} else {
				String isSupplier = obtainIsSupplier(request);
				String username = obtainUsername(request);
		        if ("Y".equals(isSupplier)) {
		        	try {
						Supplier sp = supplierService.selectByUserCodeOrTel(username);
						if (sp != null) {
							//供方编号
							SessionUtil.setSessionValue(request, SessionUtil.SUPPLIER_CREDIT_CODE, sp.getSupplierCreditCode());
							//供方名称
							SessionUtil.setSessionValue(request, SessionUtil.SUPPLIER_NAME, sp.getSupplierName());
						}
					} catch (TenderPlanException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		        }
		        super.successfulAuthentication(request, response, chain, authResult);
			}
	        //super.successfulAuthentication(request, response, authResult);
	        
	        
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request,
			HttpServletResponse response, AuthenticationException failed)
			throws IOException, ServletException {
		System.out.println("unsuccessfulAuthentication called");
		if (checkAgentIsMobile(request.getHeader("User-Agent"))) {
			response.setStatus(401); // 401:  Authentication Failed
	        HttpServletResponseWrapper responseWrapper = new HttpServletResponseWrapper(response);
	        Writer out = responseWrapper.getWriter();
	        out.write("{\"status\":1, \"message\":\"" + failed.getMessage() + "\"}");
	        out.close();
		} else {
			super.unsuccessfulAuthentication(request, response, failed);
		}
	}
    
	
	public static boolean checkAgentIsMobile(String ua) {
		boolean flag = false;
		if (!ua.contains("Windows NT") || (ua.contains("Windows NT") && ua.contains("compatible; MSIE 9.0;"))) {
			// 排除 苹果桌面系统
			if (!ua.contains("Windows NT") && !ua.contains("Macintosh")) {
				for (String item : agent) {
					if (ua.contains(item)) {
						flag = true;
						break;
					}
				}
			}
		}
		return flag;
	}
} 

