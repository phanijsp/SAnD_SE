public class Utils {
    public String appendBaseEndUrls(String baseUrl, String endUrl){
        if(endUrl.startsWith(baseUrl)){
            return endUrl;
        }else{
            return baseUrl + endUrl;
        }
    }
}
