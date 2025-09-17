package br.net.technet.cipa.Uteis;

import static android.content.Context.CONNECTIVITY_SERVICE;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Uteis {



    public static boolean verificarInternet(Context context) {

        ConnectivityManager conexao = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo informacao = conexao.getActiveNetworkInfo();

        if (informacao != null && informacao.isConnectedOrConnecting()) {

            return  true;

        } else {

            Toast.makeText(context, "Sem conexão com a internet", Toast.LENGTH_LONG).show();
            return false;

        }
    }

    public static boolean verificarCampos(Context context, String texto_1, String texto_2) {

        if (!texto_1.isEmpty() && !texto_2.isEmpty()) {

            if (verificarInternet(context)) {

                return true;
            } else {

                Toast.makeText(context, "Sem conexão com a internet", Toast.LENGTH_LONG).show();
                return  false;
            }
        } else {

            Toast.makeText(context, "Preencha os campos", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    public static void opcoesErro(Context context, String resposta) {

        if (resposta.contains("least 6 characters")) {

            Toast.makeText(context, "A senha deve conter no mínimo 6 dígitos", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("address is already")) {

            Toast.makeText(context, "E-mail já cadastrado", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("address is beadly")) {

            Toast.makeText(context, "E-mail digitado é inválido", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("interrupted connection")) {

            Toast.makeText(context, "Verifique sua conexão", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("There is not user")) {

            Toast.makeText(context, "Usuário não cadastrado", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("email invalid")) {

            Toast.makeText(context, "Insira um E-mail válido", Toast.LENGTH_LONG).show();

        } else if (resposta.contains("The supplied auth credential is incorrect")) {

            Toast.makeText(context, "Email ou Senha Incorreta", Toast.LENGTH_LONG).show();

        } else {

            Toast.makeText(context, resposta, Toast.LENGTH_LONG).show();
        }
    }
}
