package coriani.lorenzo.cookhub;
public class Ricetta {
    private String titolo;
    private String descrizione;
    private String immagineUrl;

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
