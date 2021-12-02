<script>
    import {createEventDispatcher} from 'svelte';
    import Customer from 'components/Customer.svelte';
    import Machine from 'components/Machine.svelte';
    import Site from 'components/Site.svelte';

    export let context;

    const dispatch = createEventDispatcher();

    $: customer = context.customer;
    $: domain = context.domain;
    $: machines = (context.machines) ? context.machines : [];
    $: site = context.site;
    
    $: hasMachines = (machines.length > 0);
    $: selectCustomer = () => dispatch('refresh', {customer, domain});
    $: selectDomain = () => dispatch('refresh', {domain});
    $: selectMachine = (index) => dispatch('refresh', {customer, domain, machines: machines.slice(0, index+1), site});
    $: selectSite = () => dispatch('refresh', {customer, domain, site});
</script>

<style type="text/scss">
	@import 'src/styles/vars';

    root {
        display: flex;
        flex-direction: row;
        gap: $padding;
        width: 100%;
    }

    root breadcrumbs {
        display: flex;
        flex-flow: wrap;
        flex-grow: 1;
        gap: $padding;
    }

    root home {
        display: flex;
        padding-top: 2*$padding;
    }

    root home:hover {
        cursor: pointer;
    }

    .icon {
		height: $iconSize;
		width: $iconSize;
    }
</style>

<trail>
    {#if domain && customer}
    <hr/>
    <root>
        <home on:click={selectDomain}>
            <img alt="Credersi-vend Icon" class="icon" src="images/cv-logo.png"/>
        </home>
        <breadcrumbs>
            <Customer on:refresh {context} {customer} {selectCustomer}/>
            {#if site}
            <Site on:refresh {context} {selectSite} {site}/>
            {#if hasMachines}
            {#each machines as machine, index}
            <Machine on:refresh {context} {machine} selectMachine={() => selectMachine(index)}/>
            {/each}
            {/if}
            {/if}
        </breadcrumbs>
    </root>   
    {/if}
</trail>
