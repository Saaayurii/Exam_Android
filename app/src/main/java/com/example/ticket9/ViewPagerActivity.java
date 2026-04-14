package com.example.ticket9;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import com.example.ticket9.adapter.TabsPagerAdapter;
import com.example.ticket9.databinding.ActivityViewpagerBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * Практика 2 — Билет 9.
 * Приложение с ViewPager2 и вкладками (TabLayout).
 *
 * Содержит 3 вкладки:
 *  - "Главная"  — приветственный экран
 *  - "Профиль" — экран профиля студента
 *  - "О нас"   — информация о приложении
 *
 * Связка ViewPager2 + TabLayout реализована через TabLayoutMediator.
 */
public class ViewPagerActivity extends AppCompatActivity {

    private ActivityViewpagerBinding binding;

    // Названия вкладок
    private static final String[] TAB_TITLES = {"Главная", "Профиль", "О нас"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityViewpagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Инициализируем адаптер для ViewPager2
        TabsPagerAdapter adapter = new TabsPagerAdapter(this, TAB_TITLES.length);
        binding.viewPager.setAdapter(adapter);

        // Связываем TabLayout и ViewPager2 через TabLayoutMediator
        // Медиатор автоматически синхронизирует выбранную вкладку со страницей
        new TabLayoutMediator(binding.tabLayout, binding.viewPager,
                (tab, position) -> tab.setText(TAB_TITLES[position])
        ).attach();

        // Слушатель смены страниц (опционально)
        binding.viewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                // Здесь можно реагировать на смену вкладки
            }
        });
    }
}
