package fuzzylogic;

public class ObjectData {
    
    public ObjectData(String nama, int like, int provokasi, int komentar, int emosi, Boolean isHoax){
        this.nama = nama;
        this.emosi = emosi;
        this.provokasi = provokasi;
        this.isHoax = isHoax;
        this.like = like;
        this.komentar = komentar;
    }
    
    private String nama;
    private int emosi;
    private int provokasi;
    private int komentar;
    private int like;
    private Boolean isHoax;
    
    private Integer jarak; 

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public int getKomentar() {
        return komentar;
    }

    public void setKomentar(int komentar) {
        this.komentar = komentar;
    }

    public int getLike() {
        return like;
    }

    public void setLike(int like) {
        this.like = like;
    }

    public Integer getJarak() {
        return jarak;
    }

    public void setJarak(Integer jarak) {
        this.jarak = jarak;
    }

    public int getEmosi() {
        return emosi;
    }

    public void setEmosi(int emosi) {
        this.emosi = emosi;
    }

    public int getProvokasi() {
        return provokasi;
    }

    public void setProvokasi(int provokasi) {
        this.provokasi = provokasi;
    }

    public boolean isIsHoax() {
        return isHoax;
    }

    public void setIsHoax(Boolean isHoax) {
        this.isHoax = isHoax;
    }
}
