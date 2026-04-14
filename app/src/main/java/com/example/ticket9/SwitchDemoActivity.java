package com.example.ticket9;

import android.os.Bundle;
import android.widget.CompoundButton;
import androidx.appcompat.app.AppCompatActivity;
import com.example.ticket9.databinding.ActivitySwitchBinding;

/**
 * Практика 1 — Билет 9.
 * Переключатель (Switch) с отображением текущего состояния.
 *
 * Логика:
 *  - Пользователь переключает Switch
 *  - TextView немедленно показывает текущее состояние: "ВКЛ" или "ВЫКЛ"
 *  - Цвет индикатора меняется: зелёный = ВКЛ, красный = ВЫКЛ
 */
public class SwitchDemoActivity extends AppCompatActivity {

    private ActivitySwitchBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySwitchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Устанавливаем начальное состояние UI
        updateSwitchState(binding.switchToggle.isChecked());

        // Слушатель изменений состояния Switch
        binding.switchToggle.setOnCheckedChangeListener(
                (CompoundButton buttonView, boolean isChecked) -> {
                    updateSwitchState(isChecked);
                });
    }

    /**
     * Обновляет UI в зависимости от состояния переключателя.
     *
     * @param isChecked true — переключатель включён, false — выключен
     */
    private void updateSwitchState(boolean isChecked) {
        if (isChecked) {
            binding.tvStatus.setText(getString(R.string.switch_on));
            binding.tvStatus.setTextColor(getColor(R.color.switch_on_color));
            binding.viewIndicator.setBackgroundColor(getColor(R.color.switch_on_color));
        } else {
            binding.tvStatus.setText(getString(R.string.switch_off));
            binding.tvStatus.setTextColor(getColor(R.color.switch_off_color));
            binding.viewIndicator.setBackgroundColor(getColor(R.color.switch_off_color));
        }
    }
}
