package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.UserCategoryBean;
import in.cdac.university.usm.repository.UserCategoryRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserCategoryService {

    @Autowired
    private UserCategoryRepository userCategoryRepository;

    public List<UserCategoryBean> getAllUserCategories() {
        return BeanUtils.copyListProperties(userCategoryRepository.findAll(), UserCategoryBean.class);
    }

}
