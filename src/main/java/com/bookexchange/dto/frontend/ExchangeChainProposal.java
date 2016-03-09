package com.bookexchange.dto.frontend;

import com.bookexchange.dto.User;
import com.bookexchange.graph.Graph;

import java.util.List;

/**
 * Created by sheke on 3/3/2016.
 */
public class ExchangeChainProposal {
    private List<User> chain;
    private Graph closedComponent;

    public ExchangeChainProposal() {
    }

    public ExchangeChainProposal(List<User> chain, Graph closedComponent) {
        this.chain = chain;
        this.closedComponent = closedComponent;
    }

    public List<User> getChain() {
        return chain;
    }

    public Graph getClosedComponent() {
        return closedComponent;
    }
}
