package com.itheima.web.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itheima.domain.Cart;
import com.itheima.domain.CartItem;
import com.itheima.domain.Order;
import com.itheima.domain.OrderItem;
import com.itheima.domain.PageBean;
import com.itheima.domain.User;
import com.itheima.service.OrderService;
import com.itheima.utils.BeanFactory;
import com.itheima.utils.UUIDUtils;

/**
 * Order module
 */
public class OrderServlet extends BaseServlet {

	/**
	 * 生成订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	public String add(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//0.判断用户是否登录
		User user = (User) request.getSession().getAttribute("user");
		if(user ==null) {
			request.setAttribute("msg", "Please login first");
			return "/jsp/msg.jsp";
		}
		
		//1.封装数据
		Order order = new Order();
		//1.1订单id
		order.setOid(UUIDUtils.getId());
		
		//1.2 订单时间
		order.setOrdertime(new Date());
		
		//1.3 总金额
		//获取session中的cart
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		order.setTotal(cart.getTotal());
		
		//1.4订单的所有订单项
		/*
		 * 先获取cart中的items
		 * 遍历items, 组装成orderItem
		 * 将orderitem添加到list(items)中
		 */
		Collection<CartItem> items = cart.getItems();
		for(CartItem cartItem: cart.getItems()) {
			OrderItem oi = new OrderItem();
			
			//设置id
			oi.setItemid(UUIDUtils.getId());
			//设置购买数量
			oi.setCount(cartItem.getCount());
			//设置小计
			oi.setSubtotal(cartItem.getSubtotal());
			//设置product
			oi.setProduct(cartItem.getProduct());
			//设置order
			oi.setOrder(order);
			
			//添加到list中
			order.getItems().add(oi);
		}

		//1.5设置用户
		order.setUser(user);
		
		
		//2.调用service    添加订单
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		os.add(order);
				
		//3.将order放入request域中,请求转发
		request.setAttribute("bean", order);
		
		//4.清空购物车
		request.getSession().removeAttribute("cart");
		return "/jsp/order_info.jsp";
	}

	/**
	 * 分页查询我的订单
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public String findAllByPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		//1.获取当前页
		int currPage = Integer.parseInt(request.getParameter("currPage"));
		int pageSize = 3;
		
		//2.获取用户
		User user = (User) request.getSession().getAttribute("user");
		if(user==null) {
			request.setAttribute("msg", "not logged in yet, pls log in");
			return "/jsp/msg.jsp";
		}
		
		//3.调用service 分页查询   参数:currPage pagesize user  返回值:pageBean
		OrderService os = (OrderService) BeanFactory.getBean("OrderService");
		PageBean<Order> bean = os.findAllByPage(currPage, pageSize, user);
		
		//4.将pageBean放入request域中
		request.setAttribute("pb", bean);
		
		return "/jsp/order_list.jsp";
	}
	
}
