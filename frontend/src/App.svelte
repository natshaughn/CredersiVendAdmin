<script>
	import CustomerList from 'components/CustomerList.svelte';
	import Header from 'components/Header.svelte';
	import MachineList from 'components/MachineList.svelte';
	import SiteList from 'components/SiteList.svelte';
	import SiteOrigin from 'components/SiteOrigin.svelte';
	import Trail from 'components/Trail.svelte';

	let context = {
		customer: null,
		domain: null,
		machines: [],
		site: null
	};

	$: hasMachines = (context && context.machines && (context.machines.length > 0));

	const refresh = (event) => {
		context = event.detail;
	};

	fetch('/api/v1/domains')
		.then((response) => response.json())
		.then((domain) => refresh({detail: {domain}}))
		.catch((err) => alert('Failed to fetch domains!'));
</script>

<style type="text/scss">
	@import 'src/styles/vars';

	content {
		flex-grow: 1;
		padding: $padding*2 $padding $padding $padding;
	}
    
	frontend {
		display: flex;
		flex-direction: column;
		height: 100%;
		margin-left: auto;
		margin-right: auto;
		max-width: $maxWidth;
	}
</style>

<frontend>
	<Header {context}/>
	<Trail {context} on:refresh={refresh}/>
	{#if context.domain}
	<content>
		<hr/>
		{#if hasMachines}
		<MachineList {context} on:refresh={refresh}/>
		{:else if context.site}
		<SiteOrigin {context} on:refresh={refresh}/>
		{:else if context.customer}
		<SiteList {context} on:refresh={refresh}/>
		{:else}
		<CustomerList {context} on:refresh={refresh}/>
		{/if}
	</content>
	{/if}
</frontend>
