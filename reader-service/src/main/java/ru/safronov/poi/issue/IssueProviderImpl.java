package ru.safronov.poi.issue;

import java.util.List;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import ru.safronov.core.domain.Issue;
import ru.safronov.core.port.IssueProvider;
import ru.safronov.poi.BaseProvider;

@Service
public class IssueProviderImpl extends BaseProvider implements IssueProvider {

  protected IssueProviderImpl(ReactorLoadBalancerExchangeFilterFunction loadBalancer) {
    super(loadBalancer);
  }

  @Override
  public List<Issue> findAllById(Long readerId) {

    List<IssueDto> issueDtoList = webClient.get()
        .uri("http://issue-service/reader/" + readerId)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<IssueDto>>() {})
        .block();

    assert issueDtoList != null;
    return IssueMapper.mapToIssueList(issueDtoList);
  }
}
