package com.example.ticket9;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ticket9.databinding.ActivityMainBinding;

/**
 * Главная активность — меню для выбора практической части.
 * Билет 9, студент: Долженко Роман Павлович, 09.03.01 ИВТ-1, ДонГУ
 */
public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Переход к Практике 1 — Switch
        binding.btnPractice1.setOnClickListener(v -> {
            Intent intent = new Intent(this, SwitchDemoActivity.class);
            startActivity(intent);
        });

        // Переход к Практике 2 — ViewPager с вкладками
        binding.btnPractice2.setOnClickListener(v -> {
            Intent intent = new Intent(this, ViewPagerActivity.class);
            startActivity(intent);
        });
    }
}
