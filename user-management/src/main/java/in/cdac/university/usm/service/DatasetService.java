package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.DatasetBean;
import in.cdac.university.usm.repository.DatasetRepository;
import in.cdac.university.usm.util.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    public List<DatasetBean> getAllDataset() {
        return BeanUtils.copyListProperties(datasetRepository.findAllByGblIsvalidOrderByGstrDatasetName(1), DatasetBean.class);
    }
}
