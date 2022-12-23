package in.cdac.university.usm.service;

import in.cdac.university.usm.bean.*;
import in.cdac.university.usm.entity.UmmtDatasetFilterMst;
import in.cdac.university.usm.entity.UmmtDatasetMst;
import in.cdac.university.usm.repository.*;
import in.cdac.university.usm.util.BeanUtils;
import in.cdac.university.usm.util.Language;
import in.cdac.university.usm.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

@Service
public class DatasetService {

    @Autowired
    private DatasetRepository datasetRepository;

    @Autowired
    private DBSchemaRepository dbSchemaRepository;

    @Autowired
    private TableRepository tableRepository;
    @Autowired
    private ColumnRepository columnRepository;

    @Autowired
    private DatasetFilterRepository datasetFilterRepository;
    @Autowired
    private Language language;
    public List<DatasetBean> getAllDataset() {
        return BeanUtils.copyListProperties(datasetRepository.findAllByGblIsvalidOrderByGstrDatasetName(1), DatasetBean.class);
    }

    public List<SchemaBean> getSchemaWiseModuleList() {
        return BeanUtils.copyListProperties(dbSchemaRepository.findAllByGblIsvalidOrderByGstrModuleName(1), SchemaBean.class);
    }

   public List<TableBean> getTableListBySchemaName(String schemaName) {
       return BeanUtils.copyListProperties(tableRepository.findAllOrderByGstrSchemaName(schemaName), TableBean.class);
   }

   public  List<ColumnBean> getColumnListByTableName(String tableName,String schemaName) {
        return BeanUtils.copyListProperties(columnRepository.findAllByTableNameAndSchemaName(tableName,schemaName), ColumnBean.class);
   }

   @Transactional
    public ServiceResponse save(DatasetBean datasetBean) {
       //check for duplicate dataset name
       Optional<UmmtDatasetMst> ummtDatasetMstOptional = datasetRepository.findByGstrDatasetNameIgnoreCaseAndGblIsvalidNot(datasetBean.getGstrDatasetName(),0);
       if(ummtDatasetMstOptional.isPresent()) {
           return  ServiceResponse.errorResponse(language.duplicate("Dataset Name",datasetBean.getGstrDatasetName()));
       }

       //generate new dataset Id
       Integer datasetId = datasetRepository.generateDataSetId();
       datasetBean.setGnumDatasetId(datasetId);

       UmmtDatasetMst ummtDatasetMst = BeanUtils.copyProperties(datasetBean, UmmtDatasetMst.class);

       datasetRepository.save(ummtDatasetMst);
       Integer slno = 1;

       List<UmmtDatasetFilterMst> ummtDatasetFilterMstList = new ArrayList<>();

       List<DatasetFilterBean> datasetFilterList = datasetBean.getDatasetFilterList();

       if(datasetFilterList != null && datasetFilterList.size() > 0){
        for(DatasetFilterBean datasetFilterBean : datasetFilterList) {
             UmmtDatasetFilterMst ummtDatasetFilterMst = BeanUtils.copyProperties(datasetFilterBean, UmmtDatasetFilterMst.class);
            ummtDatasetFilterMst.setGnumDatasetId(datasetId);
            ummtDatasetFilterMst.setGnumSlNo(slno);
            ummtDatasetFilterMst.setGnumModuleId(datasetFilterBean.getGnumModuleId());
            ummtDatasetFilterMst.setGstrTableName(datasetFilterBean.getGstrTableName());
            ummtDatasetFilterMst.setGstrFilterColumnName(datasetFilterBean.getGstrFilterColumnName());
            ummtDatasetFilterMst.setGstrFilterDisplay(datasetFilterBean.getGstrFilterDisplay());
            ummtDatasetFilterMst.setGstrFilterQuery(datasetFilterBean.getGstrFilterQuery());
            ummtDatasetFilterMst.setGnumModuleId(datasetFilterBean.getGnumModuleId());
            ummtDatasetFilterMst.setGblIsvalid(1);
            ummtDatasetFilterMst.setGdtEntryDate(datasetFilterBean.getGdtEntryDate());
            ummtDatasetFilterMst.setGstrFilterOrder(slno);
            ummtDatasetFilterMstList.add(ummtDatasetFilterMst);
            slno++;
         }
          datasetFilterRepository.saveAll(ummtDatasetFilterMstList);
       }
        return ServiceResponse.successMessage(language.saveSuccess("Dataset Master "));
   }
   public List<DatasetBean> getListPage(Integer moduleId, Integer isValid){
        return BeanUtils.copyListProperties(
                datasetRepository.findAllByGnumModuleIdAndGblIsvalidOrderByGstrDatasetNameAsc(moduleId, isValid),
                DatasetBean.class);
   }

   public ServiceResponse getDatasetById(Integer datasetId) {

       if (datasetId == null) {
           return ServiceResponse.errorResponse(language.mandatory("Dataset Id"));
       }

       Optional<UmmtDatasetMst> ummtDatasetMstOptional = datasetRepository.findByGnumDatasetId(datasetId);

        if(ummtDatasetMstOptional.isEmpty())
            return ServiceResponse.errorResponse(language.notFoundForId("Dataset", datasetId));

       DatasetBean datasetBean = BeanUtils.copyProperties(ummtDatasetMstOptional.get(), DatasetBean.class);
                  datasetBean.setGstrModuleId(datasetBean.getGnumModuleId()+ "^" + datasetBean.getGstrSchemaName());

       List<UmmtDatasetFilterMst> ummtDatasetFilterMstList = datasetFilterRepository.findByGnumDatasetIdAndGblIsvalid(datasetId, 1);
       if(ummtDatasetFilterMstList.isEmpty()) {
           return ServiceResponse.errorResponse(language.notFoundForId("Dataset Filter", datasetId));
       }

       List<DatasetFilterBean> datasetFilterBeanList = BeanUtils.copyListProperties(ummtDatasetFilterMstList, DatasetFilterBean.class);

       datasetBean.setDatasetFilterList(datasetFilterBeanList);
       return ServiceResponse.successObject(datasetBean);

   }
   @Transactional

   public ServiceResponse update(DatasetBean datasetBean) {
        if(datasetBean.getGnumDatasetId() == null){
            return ServiceResponse.errorResponse(language.mandatory("Dataset Id"));
        }

        //Check if Dataset Exists
       Optional<UmmtDatasetMst> ummtDatasetMstOptional = datasetRepository.findByGnumDatasetIdAndGblIsvalidNot(datasetBean.getGnumDatasetId(), 0);
        if(ummtDatasetMstOptional.isEmpty()){
            return ServiceResponse.errorResponse(language.notFoundForId("Dataset", datasetBean.getGnumDatasetId()));
        }

        //Duplicate check
       Optional<UmmtDatasetMst> ummtDatasetMstDuplicateOptional = datasetRepository.findByGstrDatasetNameIgnoreCaseAndGnumDatasetIdNotAndGblIsvalidNot(datasetBean.getGstrDatasetName(),datasetBean.getGnumDatasetId(),0);
       if (ummtDatasetMstDuplicateOptional.isPresent()) {
           return ServiceResponse.errorResponse(language.duplicate("Dataset", datasetBean.getGstrDatasetName()));
       }


       datasetFilterRepository.deleteByGnumDatasetId(datasetBean.getGnumDatasetId());
       Integer slno = 1;

      UmmtDatasetMst datasetMst = BeanUtils.copyProperties(datasetBean, UmmtDatasetMst.class);

       Integer datasetId = datasetRepository.save(datasetMst).getGnumDatasetId();



       List<UmmtDatasetFilterMst> ummtDatasetFilterMstList = new ArrayList<>();

       List<DatasetFilterBean> datasetFilterList = datasetBean.getDatasetFilterList();

       if(datasetFilterList != null && datasetFilterList.size() > 0) {
           for (DatasetFilterBean datasetFilterBean : datasetFilterList) {
               UmmtDatasetFilterMst ummtDatasetFilterMst = BeanUtils.copyProperties(datasetFilterBean, UmmtDatasetFilterMst.class);
               ummtDatasetFilterMst.setGnumDatasetId(datasetId);
               ummtDatasetFilterMst.setGnumSlNo(slno);
               ummtDatasetFilterMst.setGnumModuleId(datasetFilterBean.getGnumModuleId());
               ummtDatasetFilterMst.setGstrTableName(datasetFilterBean.getGstrTableName());
               ummtDatasetFilterMst.setGstrFilterColumnName(datasetFilterBean.getGstrFilterColumnName());
               ummtDatasetFilterMst.setGstrFilterDisplay(datasetFilterBean.getGstrFilterDisplay());
               ummtDatasetFilterMst.setGstrFilterQuery(datasetFilterBean.getGstrFilterQuery());
               ummtDatasetFilterMst.setGblIsvalid(1);
               ummtDatasetFilterMst.setGdtEntryDate(datasetFilterBean.getGdtEntryDate());
               ummtDatasetFilterMst.setGstrFilterOrder(slno);
               ummtDatasetFilterMstList.add(ummtDatasetFilterMst);
               slno++;
           }
           datasetFilterRepository.saveAll(ummtDatasetFilterMstList);
       }
       int status = 0;
       String message;
       if (datasetId != null) {
           message = language.updateSuccess("Dataset");
           status = 1;
       } else {
           message = language.updateError("Dataset");
       }
       return ServiceResponse.builder()
               .status(status)
               .message(message)
               .build();
   }

    public ServiceResponse delete(DatasetBean datasetBean) {
        if (datasetBean.getIdsToDelete() == null || datasetBean.getIdsToDelete().length == 0) {
            return ServiceResponse.errorResponse(language.mandatory("Dataset Id"));
        }

        List<Integer> datasetIdsToDelete = Stream.of(datasetBean.getIdsToDelete())
                .map(Integer::valueOf)
                .toList();

       // Check if Dataset exists
        List<UmmtDatasetMst> ummtDatasetMstList = datasetRepository.findAllByGnumDatasetIdInAndGblIsvalidNot(datasetIdsToDelete, 0);
        if (ummtDatasetMstList.isEmpty() || ummtDatasetMstList.size() != datasetBean.getIdsToDelete().length) {
            return ServiceResponse.errorResponse(language.notFoundForId("Dataset", Arrays.toString(datasetBean.getIdsToDelete())));
        }

        for (UmmtDatasetMst datasetMst : ummtDatasetMstList) {
            datasetMst.setGblIsvalid(0);
            datasetMst.setGnumEntryBy(datasetBean.getGnumEntryBy());
        }
        datasetRepository.saveAll(ummtDatasetMstList);
        return ServiceResponse.builder()
                .status(1)
                .message(language.deleteSuccess("Dataset"))
                .build();
    }
}
