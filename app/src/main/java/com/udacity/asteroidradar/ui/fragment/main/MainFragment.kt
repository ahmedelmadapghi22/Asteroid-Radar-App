package com.udacity.asteroidradar.ui.fragment.main

import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.R
import com.udacity.asteroidradar.databinding.FragmentMainBinding

class MainFragment : Fragment() {
    lateinit var viewModel: MainViewModel
    lateinit var binding: FragmentMainBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        handleStatusAsteroid()
        handleStatusPictureOfDay()
        val adapter = AsteroidAdapter(AsteroidAdapter.OnClickListener {
            viewModel.displayAsteroidDetails(it)
        })
        viewModel.asteroid.observe(viewLifecycleOwner) {
            adapter.submitList(it)
            binding.asteroidRecycler.adapter = adapter
        }
        initNavigate()

    }

    private fun initNavigate() {
        viewModel.navigateToSelectedAsteroid.observe(viewLifecycleOwner) {
            if (it != null) {
                this.findNavController()
                    .navigate(MainFragmentDirections.actionShowDetail(it))
                viewModel.displayPropertyDetailsComplete()
            }
        }
    }

    private fun handleStatusAsteroid() {
        viewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    AsteroidStatus.LOADING -> {
                        binding.asteroidRecycler.visibility = View.GONE
                        binding.statusLoadingWheel.visibility = View.VISIBLE
                    }
                    AsteroidStatus.DONE -> {
                        binding.statusLoadingWheel.visibility = View.GONE
                        binding.asteroidRecycler.visibility = View.VISIBLE
                    }
                    AsteroidStatus.ERROR -> {
                        binding.statusLoadingWheel.visibility = View.GONE
                        binding.asteroidRecycler.visibility = View.VISIBLE
                    }
                }
            }
        }
    }

    private fun handleStatusPictureOfDay() {
        viewModel.statusPic.observe(viewLifecycleOwner) {
            it?.let {
                when (it) {
                    PictureStatus.LOADING -> {
                        binding.activityMainImageOfTheDay.setImageResource(R.drawable.loading_animation)
                    }

                    PictureStatus.ERROR -> {
                        binding.activityMainImageOfTheDay.setImageResource(R.drawable.placeholder_picture_of_day)
                    }

                    else -> {
                        setPicture()
                    }
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.show_week_menu -> {
                Log.d("TAG", "onOptionsItemSelected: 1")
                viewModel.getAsteroidOfWeek()
                return true

            }
            R.id.show_day_menu -> {
                viewModel.getAsteroidOfDay("2022-08-18")
                return true

            }
            R.id.show_saved_menu -> {
                viewModel.getAsteroid()
                Log.d("TAG", "onOptionsItemSelected: 3")
                return true

            }
        }
        return false
    }

    private fun setPicture() {
        viewModel.picture.observe(viewLifecycleOwner) {
            Picasso.get()
                .load(it.url)
                .into(binding.activityMainImageOfTheDay)
        }
    }
}
