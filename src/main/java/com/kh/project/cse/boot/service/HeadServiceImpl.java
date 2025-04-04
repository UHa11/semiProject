package com.kh.project.cse.boot.service;

import com.kh.project.cse.boot.domain.vo.Announcement;
import com.kh.project.cse.boot.domain.vo.Category;
import com.kh.project.cse.boot.domain.vo.PageInfo;
import com.kh.project.cse.boot.domain.vo.Product;
import com.kh.project.cse.boot.mappers.AnnouncementMapper;
import com.kh.project.cse.boot.mappers.ProductMapper;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadServiceImpl implements HeadService {

    private final ProductMapper productMapper;
    private final AnnouncementMapper announcementMapper;



    @Autowired
    public HeadServiceImpl(ProductMapper productMapper, AnnouncementMapper announcementMapper) {
        this.productMapper = productMapper;
        this.announcementMapper = announcementMapper;
    }


    @Override
    public int insertProduct(Product product) {
        return productMapper.insertProduct(product);
    }

    @Override
    public ArrayList<Product> searchProduct(String condition, String keyword) {
        return productMapper.searchProduct(condition,keyword);
    }

    @Override
    public ArrayList<Product> selectAllProduct() {
        ArrayList<Product> p_list =  productMapper.selectAllProduct();
        return productMapper.selectAllProduct();
    }

    @Override
    public List<Category> selectAllCategories() {
        return productMapper.selectAllCategories();
    }

    @Override
    public int insertAnnouncement(Announcement announcement){
        return announcementMapper.insertAnnouncement(announcement);
    }

    //공지사항 총수
    @Override
    public int selectAnnouncementCount(){return announcementMapper.selectAnnouncementCount();}
    //

    //공지사항 리스트
    public ArrayList<Announcement> selectAnnouncementlist(PageInfo pi){
        int offset = (pi.getCurrentPage() - 1) * pi.getBoardLimit();
        RowBounds rowBounds = new RowBounds(offset, pi.getBoardLimit());
        return announcementMapper.selectAnnouncementlist(rowBounds);
    }
    //
}
