package fr.lteconsulting.angular2gwt.client;

import com.google.gwt.core.client.GWT;

import fr.lteconsulting.angular2gwt.Component;
import fr.lteconsulting.angular2gwt.client.interop.Event;
import fr.lteconsulting.angular2gwt.client.interop.angular.Router;
import jsinterop.annotations.JsConstructor;
import jsinterop.annotations.JsMethod;
import jsinterop.annotations.JsProperty;
import jsinterop.annotations.JsType;

/**
 * Angular component managing a list of heroes.
 * 
 * <li>It gets the {@link HeroService} injected by Angular.
 * <li>Its view template and CSS styles are loaded from external files located in webapp
 * <li>It has two methods : one for getting the data list and another to add a new hero in the database
 * <li>The {@link HeroFormComponent} is declared as a directive because it is declared in the template
 * 
 * @author Arnaud Tournier
 *         (c) LTE Consulting 2016
 */
//@formatter:off
@Component(
		selector = "hero-list",
		templateUrl = "hero-list.component.html",
		styleUrls = {"hero-list.component.css"},
		directives = {HeroFormComponent.class})
//@formatter:on
@JsType
public class HeroListComponent
{
	private HeroService heroService;
	private Router router;

	@JsProperty
	private String title;

	@JsProperty
	private Hero selectedHero;

	@JsProperty
	private JsArray<Hero> heroes;

	@JsConstructor
	public HeroListComponent( HeroService heroService, Router router )
	{
		this.heroService = heroService;
		this.router = router;

		heroService.getHeroes().then( list -> heroes = list, null );

		title = "Heroes list";
	}

	@JsMethod
	protected void onSelect( Hero hero )
	{
		GWT.log( "selected " + hero );
		selectedHero = hero;
	}

	@JsMethod
	protected void gotoDetail( Hero hero )
	{
		// TODO this is to be improved : how to make an Angular application link easily
		router.navigate( JsArray.of( "Hero", new LinkDto( hero.getId() ) ) );
	}

	@JsMethod
	protected void delete( Hero hero, Event event )
	{
		event.preventDefault();

		heroService.deleteHero( hero );

		if( selectedHero == hero )
			selectedHero = null;
	}

	@JsMethod
	private void newHero()
	{
		heroService.addHero();// .then( hero -> gotoDetail( hero ), null );
	}
}
