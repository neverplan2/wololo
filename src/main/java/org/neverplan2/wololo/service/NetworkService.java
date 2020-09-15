package org.neverplan2.wololo.service;

import org.neverplan2.wololo.exception.WololoException;
import org.neverplan2.wololo.model.Address;
import org.neverplan2.wololo.model.Nic;

import java.util.List;

public interface NetworkService {

    List<Nic> getNetworkInterfaces() throws WololoException;

    List<Address> scanNetwork(String nic) throws WololoException;

}
