package controller;

import entity.Client;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

public class DataProcessor {
    private List<String> euCountries = List.of("Austria", "Italia", "Belgium", "Latvia", "Bulgaria", "Lithuania", "Croatia", "Luxembourg", "Cyprus", "Malta", "Czech Republic", "Netherlands", "Denmark", "Poland", "Estonia", "Portugal", "Finland", "Romania", "France, Metropolitan", "Slovak Republic", "Germany", "Slovenia", "Greece", "Spain", "Hungary", "Sweden", "Ireland", "United Kingdom");
    private Set<Client> localClients;

    private Predicate<Client> isFromEuAndNew = client -> !localClients.contains(client) && euCountries.contains(client.getTara());


    public DataProcessor(Set<Client> localClients) {
        this.localClients = localClients;
    }

    boolean checkIfIsNewAndFromEU(Client client) {
        return isFromEuAndNew.test(client);
    }
}