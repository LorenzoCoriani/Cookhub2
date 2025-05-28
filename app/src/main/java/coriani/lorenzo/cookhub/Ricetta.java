package coriani.lorenzo.cookhub;
public class Ricetta {
    private final String titolo;
    private final String descrizione;
    private final String immagineUrl;

    public Ricetta(String titolo, String descrizione, String immagineUrl) {
        this.titolo = titolo;
        this.descrizione = descrizione;
        this.immagineUrl = immagineUrl;
    }

    public String getTitolo() {
        return titolo;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public String getImmagineUrl() {
        return immagineUrl;
    }
}


