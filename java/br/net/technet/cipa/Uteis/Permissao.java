package br.net.technet.cipa.Uteis;

import android.app.Activity;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class Permissao {

    public  static boolean permissao(Activity activity, int requestCode, String[] permissoes) {

        List<String> list = new ArrayList<>();

        for (String permissao : permissoes) {

            boolean ok = ContextCompat.checkSelfPermission(activity, permissao) == PackageManager.PERMISSION_GRANTED;

            if (!ok) {

                list.add(permissao);
            }
        }

        if (list.isEmpty()) {

            return true;
        }

        String[] newPermissions = new String[list.size()];
        list.toArray(newPermissions);

        ActivityCompat.requestPermissions(activity, newPermissions, requestCode);
        return false;
    }
}
