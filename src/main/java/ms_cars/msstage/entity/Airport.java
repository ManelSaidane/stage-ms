package ms_cars.msstage.entity;

public enum Airport {

    TUNIS_CARTHAGE("Tunis–Carthage", "TUN"),
    MONASTIR_HABIB_BOURGUIBA("Monastir Habib Bourguiba", "MIR"),
    DJERBA_ZARZIS("Djerba–Zarzis", "DJE"),
    ENFIDHA_HAMMAMET("Enfidha–Hammamet", "NBE"),
    SFAX_THYNA("Sfax–Thyna", "SFA"),
    TOZEUR_NEFTA("Tozeur–Nefta", "TOE"),
    TABARKA_AIN_DRAHAM("Tabarka–Aïn Draham", "TBJ"),
    GAFSA_KSAR("Gafsa–Ksar", "GAF"),
    GABES_MATMATA("Gabès–Matmata", "GAE");

    private final String label;
    private final String iataCode;

    Airport(String label, String iataCode) {
        this.label = label;
        this.iataCode = iataCode;
    }

    public String getLabel() {
        return label;
    }

    public String getIataCode() {
        return iataCode;
    }

}