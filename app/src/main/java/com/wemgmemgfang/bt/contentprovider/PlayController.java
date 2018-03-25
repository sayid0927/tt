package com.wemgmemgfang.bt.contentprovider;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2018/1/1 0001.
 */

public class PlayController {

    public int Id;
    public String type;
    public String isPlay;


    public PlayController() {
    }

    public PlayController(int Id, String type, String isPlay) {
        this.Id = Id;
        this.type = type;
        this.isPlay = isPlay;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(Id);
        out.writeString(type);
        out.writeString(isPlay);
    }

    public static final Parcelable.Creator<PlayController> CREATOR = new Parcelable.Creator<PlayController>() {
        public PlayController createFromParcel(Parcel in) {
            return new PlayController(in);
        }

        public PlayController[] newArray(int size) {
            return new PlayController[size];
        }
    };

    private PlayController(Parcel in) {

        Id = in.readInt();
        type = in.readString();
        isPlay = in.readString();

    }

    @Override
    public String toString() {
        return String.format(
                "[Id:%s, type:%s, isPlay:%s]",
                Id, type, isPlay);
    }
}
