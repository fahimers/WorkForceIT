
package view;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;// No doublicates är tillåtna 

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;


// är mangaged av java server faces implementation och är tillgänglig under hela session,  kommer lässas vid startup om inte man har satt metadata-complete true i faces-config 

/**
 * En managed bean class som används för internationalization
 * @author elias
 */
@ManagedBean(name = "userData")// beans för internationalization 
@SessionScoped // kommer persist genom flera HTTP request i web applicationen.
public class ChangeLang  {
   
    private String locale;
    private static Map<String,Object> countries;
   
   static{                  //ändras inte 
      countries = new LinkedHashMap<String,Object>();// görs av typen linkedhasmap den garanterar samma ordning genom life cycle of mapen 
      countries.put("English", Locale.ENGLISH);
      countries.put("Svenska", Locale.FRENCH);
      countries.put("Italian", Locale.ITALIAN);
    }

   
    public Map<String, Object> getCountries() {
      return countries;
   }

   public String getLocale() {
      return locale;
   }

   public void setLocale(String locale) {
      this.locale = locale;
   }

    /**
     *
     * @param e tar in parmeter e som den sedan gör om till en sträng e retrunerar inget för det void 
     */
   public void localeChanged(ValueChangeEvent e){
      String newLocaleValue = e.getNewValue().toString(); //gör om det till en string
      for (Map.Entry<String, Object> entry : countries.entrySet()) { // enhanced for loop 
         if(entry.getValue().toString().equals(newLocaleValue)){
            FacesContext.getCurrentInstance().getViewRoot().setLocale((Locale)entry.getValue());     //uppdaterar enligt valda språk från användaren   
         }
      }
   }
}