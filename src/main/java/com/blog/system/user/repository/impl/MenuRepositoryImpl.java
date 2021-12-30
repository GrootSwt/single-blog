package com.blog.system.user.repository.impl;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.jpa.impl.JPAQuery;
import com.blog.base.bean.SearchData;
import com.blog.base.repository.BaseRepository;
import com.blog.system.user.model.Menu;
import com.blog.system.user.model.QMenu;
import com.blog.system.user.repository.MenuRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public class MenuRepositoryImpl extends BaseRepository implements MenuRepositoryCustom {

    @Override
    public Page<Menu> pageableMenu(SearchData searchData, Pageable pageable) {
        QMenu menu = QMenu.menu;
        BooleanBuilder where = new BooleanBuilder();
        if (searchData.hasKey("enabled")) {
            where.and(menu.enabled.eq(searchData.getStringValue("enabled")));
        }
        JPAQuery<Menu> query = queryFactory.selectFrom(menu).where(where);
        return this.search(query, pageable);
    }

    @Override
    public void deleteMenuByIdArr(Long[] idArr) {
        QMenu menu = QMenu.menu;
        queryFactory.delete(menu).where(menu.id.in(idArr)).execute();
    }

    @Override
    protected Class<?> getModelClass() {
        return Menu.class;
    }
}