package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.*;
import in.cdac.university.usm.entity.UmmtDatasetMst;
import in.cdac.university.usm.entity.UmmtUserDatasetMst;
import in.cdac.university.usm.repository.DatasetRepository;
import in.cdac.university.usm.repository.UserDatasetRepository;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.ComboUtility;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserDatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    Language language;

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    private UserDatasetRepository userDatasetRepository;
    public ServiceResponse getDatasets(Long userId,Integer datasetId) throws  IllegalAccessException{

        UmmtDatasetMst ummtDatasetMst = datasetRepository.findAllByGnumDatasetIdAndGblIsvalid(datasetId,1);

        List<UmmtUserDatasetMst> mappeddataIdList = userDatasetRepository.findByGnumUserIdAndGnumDatasetIdAndGnumIsvalidNot(userId,datasetId, 1);

        List<ComboBean> mappedDatasetCombo = ComboUtility.generateComboData(
                BeanUtils.copyListProperties(mappeddataIdList, UserDatasetBean.class)
        );
        Set<String> mappedDatasetIds = mappeddataIdList.stream().map(UmmtUserDatasetMst::getGstrColumnValue).collect(Collectors.toSet());

        String mappedIds = mappeddataIdList.stream().map(UmmtUserDatasetMst::getGstrColumnValue)
                .collect(Collectors.joining(","));


        List<Map<String, Object>> mappedDataIds;
        if(mappedIds.isEmpty()) {
            mappedDataIds = jdbcTemplate.queryForList("Select " + ummtDatasetMst.getGstrColumnName() + " ," + ummtDatasetMst.getGstrDisplayColumn() + "  from " + ummtDatasetMst.getGstrSchemaName() + "." + ummtDatasetMst.getGstrTableName());
        } else
            mappedDataIds = jdbcTemplate.queryForList("Select " + ummtDatasetMst.getGstrColumnName() + "," + ummtDatasetMst.getGstrDisplayColumn() + "  from " + ummtDatasetMst.getGstrSchemaName() + "." + ummtDatasetMst.getGstrTableName() + " where " + ummtDatasetMst.getGstrColumnName() + " not in ( " + mappedIds + " )");

        List<ComboBean> notMappedDatasetCombo = mappedDataIds.stream()
                .filter(dataset -> !mappedDatasetIds.contains(String.valueOf(dataset.get(ummtDatasetMst.getGstrColumnName()))))
                .map(map -> new ComboBean(map.get(ummtDatasetMst.getGstrColumnName()).toString() + "^" + map.get(ummtDatasetMst.getGstrDisplayColumn()).toString() , map.get(ummtDatasetMst.getGstrDisplayColumn()).toString()))
                .toList();

        MappedComboBean mappedComboBean = new MappedComboBean(mappedDatasetCombo, notMappedDatasetCombo);

        return ServiceResponse.successObject(mappedComboBean);
    }

    public ServiceResponse save(UserDatasetBean userDatasetBean) {

        //delete existing datas
        List<UmmtUserDatasetMst> mappedUserDatas = userDatasetRepository.findByGnumUserIdAndGnumIsvalid(userDatasetBean.getGnumUserId(),1);
        userDatasetRepository.deleteAllInBatch(mappedUserDatas);

        if(userDatasetBean.getMappedDatasets() != null && userDatasetBean.getMappedDatasets().length > 0) {
            mappedUserDatas.clear();
            for (int i = 0; i < userDatasetBean.getMappedDatasets().length; i++) {
                UmmtUserDatasetMst ummtUserDatasetMsts = new UmmtUserDatasetMst();
                ummtUserDatasetMsts.setGnumUserId(userDatasetBean.getGnumUserId());
                ummtUserDatasetMsts.setGnumDatasetId(userDatasetBean.getGnumDatasetId());
                ummtUserDatasetMsts.setUnumUnivId(userDatasetBean.getUnumUnivId());
                if(userDatasetBean.getGnumDatasetId().equals(userDatasetBean.getGnumDefaultDatasetId()))
                    ummtUserDatasetMsts.setGnumIsDefault(1);
                else
                    ummtUserDatasetMsts.setGnumIsDefault(0);
                ummtUserDatasetMsts.setGnumModuleId(userDatasetBean.getGnumModuleId());
                ummtUserDatasetMsts.setGnumIsvalid(1);
                String[] result = userDatasetBean.getMappedDatasets()[i].split("\\^");
                ummtUserDatasetMsts.setGstrColumnValue(result[0]);
                ummtUserDatasetMsts.setGstrDisplayValue(result[1]);
                ummtUserDatasetMsts.setGdtEntryDate(new Date());
                mappedUserDatas.add(ummtUserDatasetMsts);
            }
            userDatasetRepository.saveAll(mappedUserDatas);
        }
            return ServiceResponse.builder()
                    .status(1)
                    .message(language.saveSuccess("Data Mapping"))
                    .build();

        }

}
