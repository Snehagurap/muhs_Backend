package in.cdac.university.globalService.service;

import in.cdac.university.globalService.bean.*;
import in.cdac.university.globalService.entity.GmstDocumentMst;
import in.cdac.university.globalService.entity.GmstProcDocDtl;
import in.cdac.university.globalService.entity.GmstProcessMst;
import in.cdac.university.globalService.repository.DocumentRepository;
import in.cdac.university.globalService.repository.ProcessDocumentMappingRepository;
import in.cdac.university.globalService.repository.ProcessRepository;
import in.cdac.university.globalService.util.BeanUtils;
import in.cdac.university.globalService.util.Language;
import in.cdac.university.globalService.util.ServiceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessDocumentMappingService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private ProcessDocumentMappingRepository processDocumentMappingRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private Language language;

    public ServiceResponse getMappingDetails(Long processId) {
        List<GmstDocumentMst> allDocuments = documentRepository.getAllDocuments();

        Set<Long> mappedDocumentIds = processDocumentMappingRepository.findByUnumProcessIdAndUnumIsvalid(
                        processId, 1)
                .stream().map(GmstProcDocDtl::getUnumDocId).collect(Collectors.toSet());

        List<ComboBean> mappedDocuments = new ArrayList<>();
        List<ComboBean> notMappedDocuments = new ArrayList<>();

        for (GmstDocumentMst documentMst : allDocuments) {
            ComboBean comboBean = new ComboBean(documentMst.getUnumDocId().toString(), documentMst.getUstrDocName());
            if (mappedDocumentIds.contains(documentMst.getUnumDocId())) {
                mappedDocuments.add(comboBean);
            } else {
                notMappedDocuments.add(comboBean);
            }
        }

        return ServiceResponse.successObject(new MappedComboBean(mappedDocuments, notMappedDocuments));
    }


    public List<ProcessDocumentMappingBean> getListPageDtl() {
        List<GmstProcDocDtl> mappingData = processDocumentMappingRepository.findByUnumIsvalid(1);
        List<GmstProcessMst> processList = processRepository.findByUnumIsvalid(1);
        List<ProcessDocumentMappingBean> listPage = new ArrayList<>();
        for (GmstProcessMst P : processList) {
            String docNames = mappingData.stream().filter(p -> p.getUnumProcessId().equals(Long.valueOf(P.getUnumProcessId())))
                    .map(GmstProcDocDtl::getUstrDocFname).collect(Collectors.joining(", "));
            if (!docNames.isEmpty()) {
                ProcessDocumentMappingBean processDocumentMappingBean = new ProcessDocumentMappingBean();
                processDocumentMappingBean.setProcessName(P.getUstrProcessName());
                processDocumentMappingBean.setMappedDocumentsName(docNames);
                processDocumentMappingBean.setUnumProcessId(Long.valueOf(P.getUnumProcessId()));
                listPage.add(processDocumentMappingBean);
            }
        }
        return listPage;
    }

    @Transactional
    public ServiceResponse saveMappingDetails(ProcessDocumentMappingBean processDocumentMappingBean) {

        // Get existing Mapping Details
        List<GmstProcDocDtl> alreadyMappedDocuments = processDocumentMappingRepository.findByUnumProcessIdAndUnumIsvalid(
                processDocumentMappingBean.getUnumProcessId(), 1);

        Set<Long> mappedDocumentsSet = new HashSet<>(processDocumentMappingBean.getMappedDocuments());

        // Documents to delete
        List<GmstProcDocDtl> documentsToDelete = new ArrayList<>();
        for (GmstProcDocDtl documentDtl : alreadyMappedDocuments) {
            if (mappedDocumentsSet.contains(documentDtl.getUnumDocId())) {
                mappedDocumentsSet.remove(documentDtl.getUnumDocId());
            } else
                documentsToDelete.add(documentDtl);
        }

        if (!documentsToDelete.isEmpty()) {
            List<Long> documentIdsToDelete = documentsToDelete.stream().map(GmstProcDocDtl::getUnumDocId).toList();
            processDocumentMappingRepository.delete(processDocumentMappingBean.getUnumProcessId(), documentIdsToDelete);
        }

        // Documents to add
        List<GmstProcDocDtl> documentsToAdd = new ArrayList<>();
        for (Long documentId : mappedDocumentsSet) {
            GmstProcDocDtl gmstProcessDocumentDtl = BeanUtils.copyProperties(processDocumentMappingBean, GmstProcDocDtl.class);
            gmstProcessDocumentDtl.setUnumDocId(documentId);
            String docName = documentRepository.getDocumentName(documentId);
            gmstProcessDocumentDtl.setUstrDocFname(docName);
            gmstProcessDocumentDtl.setUstrDocSname(docName);
            documentsToAdd.add(gmstProcessDocumentDtl);
        }

        if (!documentsToAdd.isEmpty()) {
            processDocumentMappingRepository.saveAll(documentsToAdd);
        }
        return ServiceResponse.successMessage(language.saveSuccess("Process Document Mapping"));
    }

}
