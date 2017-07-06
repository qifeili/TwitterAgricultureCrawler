package jonathon.controller;

	import java.io.IOException;
	import java.sql.SQLException;
	import java.util.HashMap;
	import java.util.Map;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpServletResponse;
	import net.sf.json.JSONObject;
	import org.springframework.stereotype.Controller;
	import org.springframework.web.bind.annotation.RequestMapping;
	import org.springframework.web.bind.annotation.RequestMethod;
	import org.springframework.web.servlet.ModelAndView;
    import jonathon.Agriculture.Crawler;
    import jonathon.bean.test;
    import jonathon.service.LoginCheck;


	@Controller
	@RequestMapping
	public class AgricultureController {
		
		
	    @RequestMapping("/spring")
	    public ModelAndView test()
	    {
	        String str = "this is a SpringMVC instance!";
	       
			test.info();
	        return new ModelAndView("show","str",str);
	    }

	    @RequestMapping("/login")
	    public ModelAndView check(HttpServletRequest request)
	    {
	        String name = request.getParameter("username");
	        String password = request.getParameter("password");
	        //����ҵ����LoginCheck
	        if(LoginCheck.check(name, password))
	        {
	            return new ModelAndView("success","username",name);
	        }
	        return new ModelAndView("error","username",name);
	    }
	    @RequestMapping("/add")
	    public String add(HttpServletRequest request) throws SQLException, IOException{
	     	if(Crawler.ad()){
			return "success2";
			}
	     	else return "error";
	     	
	    };
	    @RequestMapping("/twitter")        //这个是抓Twitter数据的页面。
	    public String twitter(HttpServletRequest request){
	    
	        //	atwitter.twit();
	    	
	    	
			return "success2";
		
	     	
	    };
		
	    @RequestMapping(method=RequestMethod.GET, value="/empd")
		public JSONObject getEmployees(HttpServletRequest request, HttpServletResponse response) throws IOException {	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "json");
			map.put("bool", Boolean.TRUE);
			map.put("int", new Integer(1));
			map.put("arr", new String[] { "a", "b" });
			map.put("func", "function(i){ return this.arr[i]; }");
			JSONObject json = JSONObject.fromObject(map);
			 
			System.out.println(json);
			
			response.getWriter().print(json);       //����дresponse.getWriter().print(result);
			response.getWriter().flush();
			response.getWriter().close();
			
			return null;
		}
	    @RequestMapping(method=RequestMethod.POST, value="/empd2")
		public JSONObject getEmployees2(HttpServletRequest request, HttpServletResponse response) throws IOException {	
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("name", "qifei");
			map.put("bool", Boolean.TRUE);
			map.put("int", new Integer(1));
			map.put("arr", new String[] { "a", "b" });
			map.put("func", "function(i){ return this.arr[i]; }");
			JSONObject json = JSONObject.fromObject(map);
			 
			System.out.println(json);
			
			response.getWriter().print(json);       //����дresponse.getWriter().print(result);
			response.getWriter().flush();
			response.getWriter().close();
			
			return null;
		}
	}