package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.CountryBean;
import in.cdac.university.usm.repository.CountryRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public List<CountryBean> getAllCountries() {
        return BeanUtils.copyListProperties(countryRepository.findAllByGnumIsvalidOrderByGstrCountrynameAsc(1), CountryBean.class);
    }
}
