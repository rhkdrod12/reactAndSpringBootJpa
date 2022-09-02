package com.example.practicejpa.dao;

import com.example.practicejpa.model.MenuMgm;
import com.example.practicejpa.dto.vo.MenuVo;
import com.example.practicejpa.model.QMenuMgm;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public class MenuDao extends BaseJpaEntityDao {
	
	public List<MenuVo> getMenuList(String menuType) {
		QMenuMgm menu = QMenuMgm.menuMgm;
		
		List<MenuMgm> fetch = jpaQueryFactory.selectFrom(menu).where(menu.type.eq(menuType))
		                                     .fetch();
		
		System.out.println(fetch);
		
		
		QBean<MenuVo> bean = Projections.bean(MenuVo.class,
				menu.menuId,
				menu.name,
				menu.type,
				menu.category,
				menu.menuDepth,
				menu.menuOrder,
				menu.upperMenu,
				menu.url
		);
		
		return jpaQueryFactory.select(bean)
		                      .from(menu)
		                      .where(menu.type.eq(menuType))
		                      .fetch();
	}
	
	public Map<Long, MenuVo> getMenuMap(String menuType) {
		QMenuMgm menu = QMenuMgm.menuMgm;
		
		QBean<MenuVo> bean = Projections.bean(MenuVo.class,
				menu.menuId,
				menu.name,
				menu.type,
				menu.category,
				menu.menuDepth,
				menu.menuOrder,
				menu.upperMenu,
				menu.url
		);
		
		return jpaQueryFactory.select(bean)
		                      .from(menu)
		                      .where(menu.type.eq(menuType))
		                      .transform(GroupBy.groupBy(menu.menuId).as(bean));
	}
	
}
