package com.example.ootd_recommendation_app.ui.home;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.ootd_recommendation_app.R;
import com.example.ootd_recommendation_app.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private final ArrayList<String> totalCheckedPaths = new ArrayList<>(); // 누적 리스트

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        FloatingActionButton fab = root.findViewById(R.id.fab_home);
        fab.setOnClickListener(v -> {
            NavController navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment_content_main);
            navController.navigate(R.id.action_global_to_itemFragment);
            /*navController.navigateUp();*/
        });

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeViewModel viewModel = new ViewModelProvider(this).get(HomeViewModel.class);

        viewModel.getCheckedItems().observe(getViewLifecycleOwner(), items -> {
            String result = TextUtils.join("\n", items);
            binding.checkedItemsText.setText(result);
        });

        getParentFragmentManager().setFragmentResultListener("checklist_result", this, (requestKey, bundle) -> {
            ArrayList<String> paths = bundle.getStringArrayList("checked_items");
            if (paths != null) {

                viewModel.addCheckedItems(paths); // ViewModel에 저장
//                for (String path : paths) {
//                    if (!totalCheckedPaths.contains(path)) {
//                        totalCheckedPaths.add(path);
//                    }
//                }
//                String result = TextUtils.join("\n", totalCheckedPaths);
//                binding.checkedItemsText.setText(result);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}