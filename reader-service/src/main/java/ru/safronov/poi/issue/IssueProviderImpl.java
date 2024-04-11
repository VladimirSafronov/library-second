package ru.safronov.poi.issue;

import com.netflix.discovery.EurekaClient;
import java.util.List;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Issue;
import ru.safronov.core.port.IssueProvider;
import ru.safronov.poi.BaseProvider;

@Service
public class IssueProviderImpl extends BaseProvider implements IssueProvider {

  protected IssueProviderImpl(EurekaClient eurekaClient) {
    super(eurekaClient, "ISSUE-SERVICE");
  }

  @Override
  public List<Issue> findAllById(Long readerId) {

    List<IssueDto> issueDtoList = webClient.get()
        .uri(getServiceIp(serviceName) + "/reader/" + readerId)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<IssueDto>>() {})
        .block();

    assert issueDtoList != null;
    return IssueMapper.mapToIssueList(issueDtoList);
  }
}
