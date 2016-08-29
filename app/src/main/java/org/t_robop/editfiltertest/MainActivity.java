package org.t_robop.editfiltertest;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

//ひらがなが入力されたらエラーを出すやつ
public class MainActivity extends AppCompatActivity {

    //EditTextの宣言
    EditText editText;
    //InputLayoutの宣言
    TextInputLayout inputLayoutTest;
    //キーボード用Managerの宣言
    InputMethodManager inputMethodManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //EditTextの関連付け
        editText = (EditText) findViewById(R.id.editText);
        //inputLayoutの関連付け(エラー表記のため)
        inputLayoutTest = (TextInputLayout) findViewById(R.id.inputLayoutTest);
        //キーボード表示を制御（出したり消したり）するためのオブジェクトの関連付け
        inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        //エラー文の指定
        inputLayoutTest.setError("");
        //エラー文を表示
        inputLayoutTest.setErrorEnabled(true);
        //EditTextの入力時Filterセット
        editTextFilter(editText,inputLayoutTest);
    }

    //EditTextの入力を英数字限定にするFilter
    public void editTextFilter(EditText editText,TextInputLayout inputLayoutTest){
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                //英数字の場合
                if (source.toString().matches("^[a-zA-Z0-9_]+$")) {
                    //エラー文を非表示に
                    inputLayoutTest.setError("");
                    //入力値を問題なく返す
                    return source;
                }
                //英数字でない場合
                else {
                    //文字列が0(空白)でない時(改行等の処理を含ませないため)
                    if(end!=0) {
                        //エラー文の指定
                        //英語限定
                        inputLayoutTest.setError("Error Occurred !");
                        //キーボードを閉じる
                        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                    }
                    //空白を返す
                    return "";
                }
            }
        };
        //設定したイベントからFilterの作成
        InputFilter[] filters = { inputFilter };
        //作成したFilterのセット
        editText.setFilters(filters);
    }
}
