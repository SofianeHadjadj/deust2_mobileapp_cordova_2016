package com.deust.applicationmescontacts;

import android.os.Parcel;
import android.os.Parcelable;

public class Contact implements Parcelable{
	  
	 private String nom_prenom = null;
	 private String telephone = null;
	 boolean selected = false;
	  
	 public Contact(String nom_prenom, String telephone, boolean selected) {
	  super();
	  this.nom_prenom = nom_prenom;
	  this.telephone = telephone;
	  this.selected = selected;
	 }
	  
	 public String getNom_prenom() {
	  return nom_prenom;
	 }
	 public void setNom_prenom(String nom_prenom) {
	  this.nom_prenom = nom_prenom;
	 }
	 public String getTelephone() {
	  return telephone;
	 }
	 public void setTelephone(String telephone) {
	  this.telephone = telephone;
	 }
	 
	 public boolean isSelected() {
	  return selected;
	 }
	 public void setSelected(boolean selected) {
	  this.selected = selected;
	 }
	 
	 
	 
	    private Contact(Parcel in) {
	        // This order must match the order in writeToParcel()
	        this.nom_prenom = in.readString();
	        this.telephone = in.readString();
	        // Continue doing this for the rest of your member data
	    }

	    public void writeToParcel(Parcel out, int flags) {
	        // Again this order must match the Question(Parcel) constructor
	        out.writeString(this.nom_prenom);
	        out.writeString(this.telephone);
	        // Again continue doing this for the rest of your member data
	    }

		@Override
		public int describeContents() {
			// TODO Auto-generated method stub
			return 0;
		}
		
		
		public static final Parcelable.Creator<Contact> CREATOR = new Parcelable.Creator<Contact>()
				{
				    @Override
				    public Contact createFromParcel(Parcel source)
				    {
				        return new Contact(source);
				    }
				 
				    @Override
				    public Contact[] newArray(int size)
				    {
				    return new Contact[size];
				    }
				};
				
				

				 


}
