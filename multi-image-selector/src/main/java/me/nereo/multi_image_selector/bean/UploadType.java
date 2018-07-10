package me.nereo.multi_image_selector.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/9/22 0022.
 * 是否 参加活动
 */
public class UploadType implements Parcelable {

    /**
     * 参加话题填写话题ID
     参加活动填写活动ID     type_id
     不参加为0
     */

    /**
     * 	类型    type
     * 	 topic 参加话题

         activity 参加活动

         不参加为空
     */

    private String type_id ;
    private String type ;
    private String showString ;

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getShowString() {
        return showString;
    }

    public void setShowString(String showString) {
        this.showString = showString;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type_id);
        dest.writeString(this.type);
        dest.writeString(this.showString);
    }

    public UploadType() {
    }

    protected UploadType(Parcel in) {
        this.type_id = in.readString();
        this.type = in.readString();
        this.showString = in.readString();
    }

    public static final Creator<UploadType> CREATOR = new Creator<UploadType>() {
        @Override
        public UploadType createFromParcel(Parcel source) {
            return new UploadType(source);
        }

        @Override
        public UploadType[] newArray(int size) {
            return new UploadType[size];
        }
    };

    @Override
    public String toString() {
        return "UploadType{" +
                "type_id='" + type_id + '\'' +
                ", type='" + type + '\'' +
                ", showString='" + showString + '\'' +
                '}';
    }
}
