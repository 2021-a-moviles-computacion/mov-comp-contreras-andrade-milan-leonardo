package com.example.replicacionimdb.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.replicacionimdb.R
import com.example.replicacionimdb.databinding.FragmentHomeBinding
import com.example.replicacionimdb.ui.adapters.ExibidoHoyAdapter
import com.example.replicacionimdb.ui.adapters.PeliculaAdapter
import com.example.replicacionimdb.ui.adapters.ViewPagerAdapter2
import com.example.replicacionimdb.ui.clases.ExibidoHoy
import com.example.replicacionimdb.ui.clases.Pelicula
import com.example.replicacionimdb.ui.clases.Persona
import com.example.replicacionimdb.ui.clases.Slide

class HomeFragment : Fragment() {
    private var lstSlides= ArrayList<Slide>()

    private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root



        //Slides
        lstSlides=generarSlider()
        val adapter = ViewPagerAdapter2(lstSlides)

        val pager = root.findViewById<ViewPager2>(R.id.slidier_pager)
        pager.adapter = adapter


        //Exhibidas hoy
        val exhibidosHoyAdapter = ExibidoHoyAdapter(generarExhibidoHoy())

        var exhibidosHoyRV = root.findViewById<RecyclerView>(R.id.rv_ExhibidosHoy)
        exhibidosHoyRV.adapter = exhibidosHoyAdapter
        exhibidosHoyRV.layoutManager = LinearLayoutManager(context,
            LinearLayoutManager.HORIZONTAL, false)


        //Peliculas
        val peliculasAdapter = PeliculaAdapter(generarPeliculas(),findNavController())

        var PeliculasRV = root.findViewById<RecyclerView>(R.id.rv_Peliculas)
        PeliculasRV.adapter = peliculasAdapter
        PeliculasRV.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)




        //val anadirALista = root.findViewById<ImageButton>(R.id.btn_anadirLista)

        //anadirALista.setOnClickListener {
       // findNavController().navigate(R.id.navigation_actividadDetallePelicula)
       //}


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun generarSlider():ArrayList<Slide>{
        var lista = ArrayList<Slide>()
        lista.add(
            Slide(
                R.drawable.pl_sl_house_of_gucci,
                R.drawable.pl_p_house_of_gucci,
                "House of Gucci",
                "Official Trailer"
            )
        )
        lista.add(
            Slide(
                R.drawable.pl_sl_house_of_gucci,
                R.drawable.pl_p_house_of_gucci,
                "harry potter",
                "Official Trailer"
            )
        )

        lista.add(
            Slide(
                R.drawable.pl_sl_house_of_gucci,
                R.drawable.pl_p_house_of_gucci,
                "Hola 3",
                "Official Trailer"
            )
        )
        return  lista
    }

    private fun generarExhibidoHoy():ArrayList<ExibidoHoy>{
        var lista = ArrayList<ExibidoHoy>()

        lista.add(
            ExibidoHoy(
                R.drawable.eh_the_best_25_august,
                "The 25 Best Things to Watch in August",
                "List"
            )
        )
        lista.add(
            ExibidoHoy(
                R.drawable.eh_stars_at_the_beach,
                "Vintage Looks: Stars at the Beach",
                "Photos"
            )
        )


        return  lista
    }

    private fun generarPeliculas():ArrayList<Pelicula>{
        var lista = ArrayList<Pelicula>()

        var categoriasPelicula1 = ArrayList<String>()
        categoriasPelicula1.add("Action")
        categoriasPelicula1.add("Adventure")
        categoriasPelicula1.add("Sci-Fi")

        lista.add(
            Pelicula(
                R.drawable.pl_p_black_widow,
                R.drawable.pl_t_black_widow2,
                "Viuda Negra 1",
                "6.8",
                "2021",
                "2021 12 2h 14min",
                "Las aventuras en solitario de la Viuda Negra. 1",
                "Cate Shortland",
                "Eric Pearson(screenplay by)Jac Schaeffer(story by)Ned Benson(story by)",
                categoriasPelicula1,
                generarPersonas()


            )
        )

        lista.add(
            Pelicula(
                R.drawable.pl_p_black_widow,
                R.drawable.pl_t_black_widow2,
                "Viuda Negra 2",
                "6.8",
                "2021",
                "2021 12 2h 14min",
                "Las aventuras en solitario de la Viuda Negra. 2",
                "Cate Shortland",
                "Eric Pearson(screenplay by)Jac Schaeffer(story by)Ned Benson(story by)",
                categoriasPelicula1,
                generarPersonas()

            )
        )

        lista.add(
            Pelicula(
                R.drawable.pl_p_black_widow,
                R.drawable.pl_t_black_widow2,
                "Viuda Negra 3",
                "6.8",
                "2021",
                "2021 12 2h 14min",
                "Las aventuras en solitario de la Viuda Negra.3",
                "Cate Shortland",
                "Eric Pearson(screenplay by)Jac Schaeffer(story by)Ned Benson(story by)",
                categoriasPelicula1,
                generarPersonas()

            )
        )

        return  lista
    }


    private fun generarPersonas():ArrayList<Persona>{
        var lista = ArrayList<Persona>()

        lista.add(
            Persona(
                R.drawable.pr_scarlet_johanson,
                "Scarleth Johanson",
                "Natasha Romanoff"
            )
        )
        lista.add(
            Persona(
                R.drawable.pr_scarlet_johanson,
                "Scarleth Johanson2",
                "Natasha Romanoff"
            )
        )
        lista.add(
            Persona(
                R.drawable.pr_scarlet_johanson,
                "Scarleth Johanson3",
                "Natasha Romanoff"
            )
        )

        return lista
    }
}