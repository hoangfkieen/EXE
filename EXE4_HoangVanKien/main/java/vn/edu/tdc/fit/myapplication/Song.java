package vn.edu.tdc.fit.myapplication;

public class Song {
    public Song(String tenbaihat, int file) {
        this.tenbaihat = tenbaihat;
        File = file;
    }

    public String getTenbaihat() {
        return tenbaihat;
    }

    public void setTenbaihat(String tenbaihat) {
        this.tenbaihat = tenbaihat;
    }

    public int getFile() {
        return File;
    }

    public void setFile(int file) {
        File = file;
    }

    private String tenbaihat;
    private int File;

}
