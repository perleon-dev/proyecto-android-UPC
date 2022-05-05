package com.example.fast_service;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class SucursalesFragment extends Fragment implements OnMapReadyCallback {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Double latitud1, longitud1, latitud2, longitud2, latitud3, longitud3;
    List<Marker> listaMarcadores = new ArrayList<>();
    GoogleMap mMap;


    public SucursalesFragment() {

    }

    public static SucursalesFragment newInstance(String param1, String param2) {
        SucursalesFragment fragment = new SucursalesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Sucursales");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_sucursales, container, false);
        // TextView textView11 = view.findViewById(R.id.textView11);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        setupUI();
        myInterfaz();

    }

    private void myInterfaz() {
        latitud1 = -12.049225;
        longitud1 = -77.070997;

        latitud2 = -12.118609;
        longitud2 = -77.014503;

        latitud3 = -12.093034;
        longitud3 = -76.984930;

        LatLng fastService1 = new LatLng(latitud1, longitud1);
        LatLng fastService2 = new LatLng(latitud2, longitud2);
        LatLng fastService3 = new LatLng(latitud3, longitud3);

        MarkerOptions markerOptions1 = new MarkerOptions();
        MarkerOptions markerOptions2 = new MarkerOptions();
        MarkerOptions markerOptions3 = new MarkerOptions();

        markerOptions1.position(fastService1).title("Fast Service Benavides").snippet("Sucursal 1");
        markerOptions2.position(fastService2).title("Fast Service Pedro Venturo").snippet("Sucursal 2");
        markerOptions3.position(fastService3).title("Fast Service San Borja").snippet("Sucursal 3");

        Marker marker1 = mMap.addMarker(markerOptions1);
        Marker marker2 = mMap.addMarker(markerOptions2);
        Marker marker3 = mMap.addMarker(markerOptions3);

        listaMarcadores.add(marker1);
        listaMarcadores.add(marker2);
        listaMarcadores.add(marker3);

        centrarMarcadores(listaMarcadores);
    }

    private void centrarMarcadores(List<Marker> listaMarcadores) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (Marker marker : listaMarcadores) {
            builder.include(marker.getPosition());
        }
        mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 150));
    }

    private void setupUI() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setMyLocationButtonEnabled(true);
    }
}