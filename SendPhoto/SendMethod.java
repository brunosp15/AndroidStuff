ckage br.com.consultasaude.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.transition.TransitionManager;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import br.com.consultasaude.DoctorHealthApp;
import br.com.consultasaude.R;
import br.com.consultasaude.manager.ConsultationManager;
import br.com.consultasaude.model.Agreement;
import br.com.consultasaude.model.AgreementPlan;
import br.com.consultasaude.model.User;
import br.com.consultasaude.network.Callback;
import br.com.consultasaude.network.response.AgreementPlansResponse;
import br.com.consultasaude.network.response.AgreementsResponse;
import br.com.consultasaude.network.response.LoginResponse;
import br.com.consultasaude.network.services.AccessService;
import br.com.consultasaude.network.services.ConsultationService;
import br.com.consultasaude.utils.FileUtils;
import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;

/**
 * Created by bruno on 6/9/16.
 */
public class RegisterActivity extends BaseActivity {

    AccessService accessService;

    private void sendPhoto(int userId, Uri fileUri) {
        try {
            File file = FileUtils.getFileFromUri(fileUri, this);

            MultipartBody.Part filePart = MultipartBody.Part.createFormData("imagem", file.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), file));
            RequestBody key = RequestBody.create(MediaType.parse("multipart/form-data"), "s2PeofRhs7dA8Lt201Uo");
            RequestBody userIdPart = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(userId));
            accessService.sendUserPhoto("http://www.doctorhealthapp.com.br/app/inc/foto.php", userIdPart, key, filePart).enqueue(new Callback<LoginResponse>() {
                @Override
                public void onSuccess(Response<LoginResponse> response) {
                }

                @Override
                public void onError(Call<LoginResponse> call) {
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
