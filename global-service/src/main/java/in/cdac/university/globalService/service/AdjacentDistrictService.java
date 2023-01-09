package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.GmstAdjacentDistrictDtl;
import in.cdac.university.globalService.repository.AdjacentDistrictRepository;
import in.cdac.university.globalService.util.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AdjacentDistrictService {

    @Autowired
    private RestUtility restUtility;

    @Autowired
    private AdjacentDistrictRepository adjacentDistrictRepository;

    @Autowired
    private Language language;
    public ServiceResponse getAdjacentDistricts(Integer stateCode,Integer distCode) throws IllegalAccessException{
        // Get District List
        DistrictBean[] districts = restUtility.get(RestUtility.SERVICE_TYPE.USM, Constants.URL_GET_DISTRICTS_BY_STATE_ID +" / "+ stateCode, DistrictBean[].class);

        DistrictBean notDistrict= restUtility.get(RestUtility.SERVICE_TYPE.USM, Constants.URL_GET_DISTRICT_BY_DISTRICT_ID +" / "+ distCode,DistrictBean.class);

        if (districts == null) {
            return ServiceResponse.successObject(new ArrayList<>());
        }
        DistrictBean districtBean = notDistrict;
        Set<Integer> district = Collections.singleton(districtBean.getNumDistId());

        List<DistrictBean> districtBeans = Arrays.asList(districts);

        List<DistrictBean> districtBeanList = districtBeans.stream().filter(dist -> !district.contains(dist.getNumDistId())).toList();

        List<GmstAdjacentDistrictDtl> mappedAdjDistricts = adjacentDistrictRepository.findByNumDistIdAndUnumIsvalid(distCode, 1);

        Set<Integer> mappedAdjDistrictIds = mappedAdjDistricts.stream().map(GmstAdjacentDistrictDtl::getNumAdjacentDistId).collect(Collectors.toSet());

        List<ComboBean> mappedAdjacentDistrictsCombo = ComboUtility.generateComboData(
                BeanUtils.copyListProperties(
                        districtBeanList.stream().filter(dist -> mappedAdjDistrictIds.contains(dist.getNumDistId())).toList(),
                        DistrictBean.class
                )
        );

        List<ComboBean> notMappedAdjacentDistrictsCombo = ComboUtility.generateComboData(
                BeanUtils.copyListProperties(
                        districtBeanList.stream().filter(dist -> !mappedAdjDistrictIds.contains(dist.getNumDistId())).toList(),
                        DistrictBean.class
                )
        );
        MappedComboBean mappedComboBean = new MappedComboBean(mappedAdjacentDistrictsCombo, notMappedAdjacentDistrictsCombo);
        return ServiceResponse.successObject(mappedComboBean);
    }


    @Transactional
    public ServiceResponse saveMappingDetails(AdjacentDistrictBean adjacentDistrictBean) {
        // Get existing Mapping Details
        List<GmstAdjacentDistrictDtl> alreadyMappedDistricts = adjacentDistrictRepository.findByUnumIsvalidAndGnumStatecodeAndNumDistId(
                1, adjacentDistrictBean.getGnumStatecode(), adjacentDistrictBean.getNumDistId()
        );

        Set<Integer> mappedAdjacentDistrictSet = new HashSet<>(adjacentDistrictBean.getMappedAdjacentDistricts());

        // Adjacent Districts to delete
        List<GmstAdjacentDistrictDtl> adjacentDistrictToDelete = new ArrayList<>();
        for (GmstAdjacentDistrictDtl districtMst: alreadyMappedDistricts) {
            if (mappedAdjacentDistrictSet.contains(districtMst.getNumAdjacentDistId()))
                mappedAdjacentDistrictSet.remove(districtMst.getNumAdjacentDistId());
            else
                adjacentDistrictToDelete.add(districtMst);
        }
        if (!adjacentDistrictToDelete.isEmpty()) {
            List<Integer> adjdistrictIdsToDelete = adjacentDistrictToDelete.stream().map(GmstAdjacentDistrictDtl::getNumAdjacentDistId).toList();
            adjacentDistrictRepository.delete(adjacentDistrictBean.getGnumStatecode(), adjacentDistrictBean.getNumDistId(), adjdistrictIdsToDelete);
        }

        // Adjacent Districts to add
        List<GmstAdjacentDistrictDtl> AdjacentDistrictsToAdd = new ArrayList<>();
        Integer priorityNo = 1;
        for (Integer adjacentdistrictId: mappedAdjacentDistrictSet) {
            GmstAdjacentDistrictDtl adjacentDistrictMst = BeanUtils.copyProperties(adjacentDistrictBean, GmstAdjacentDistrictDtl.class);
            adjacentDistrictMst.setNumAdjacentDistId(adjacentdistrictId);
            adjacentDistrictMst.setNumAdjacencyPriority(priorityNo);
            AdjacentDistrictsToAdd.add(adjacentDistrictMst);
            priorityNo ++;
        }

        if (!AdjacentDistrictsToAdd.isEmpty())
            adjacentDistrictRepository.saveAll(AdjacentDistrictsToAdd);

        return ServiceResponse.builder()
                .status(1)
                .message(language.saveSuccess("Adjacent District Mapping"))
                .build();
    }
}
