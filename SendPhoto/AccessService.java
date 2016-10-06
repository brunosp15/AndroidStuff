ckage br.com.consultasaude.network.services;

import br.com.consultasaude.network.response.LoginResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface AccessService {


    @POST
    @Multipart
    Call<LoginResponse> sendUserPhoto(@Url String url, @Part("usuario") RequestBody userId, @Part("key") RequestBody key, @Part MultipartBody.Part file);


}

