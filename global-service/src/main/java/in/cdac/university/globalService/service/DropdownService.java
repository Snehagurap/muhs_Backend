package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.DropdownBean;
import in.cdac.university.globalService.repository.DropdownRepository;
import in.cdac.university.globalService.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DropdownService {

    @Autowired
    private DropdownRepository dropdownRepository;

    public List<DropdownBean> getDropdown(Long dropdownId) {
        return BeanUtils.copyListProperties(
                dropdownRepository.findByUnumIsvalidAndUnumDropdownIdOrderByUnumOrderNoAsc(1, dropdownId),
                DropdownBean.class
        );
    }
}
