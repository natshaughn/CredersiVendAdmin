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
		const url = new URL(window.location.href);

		if (event.root !== true) {
			context = event.detail;
		} else {
			const domain = event.detail.domain;
			const domainUuid = event.detail.domain.uuid;
			const domainUrl = `/api/v1/domains/${domainUuid}`;
			const customerUuid = url.searchParams.get('customer');
			const siteUuid = url.searchParams.get('site');
			const machineUuids = url.searchParams.get('machines');

			if ((customerUuid != null) && (customerUuid.length > 0)) {
				const customerUrl = domainUrl+`/customers/${customerUuid}`;
				fetch(customerUrl)
					.then((response) => response.json())
					.then((customer) => {
						if ((siteUuid != null) && (siteUuid.length > 0)) {
							const siteUrl = customerUrl+`/sites/${siteUuid}`;
							fetch(siteUrl)
								.then((response) => response.json())
								.then(async (site) => {
									if ((machineUuids != null) && (machineUuids.length > 0)) {
										const machines = [];
										const split = machineUuids.split(' ');
										
										for (let machineUuid of split) {
											const machineResult = await fetch(siteUrl+`/machines/${machineUuid}`);
											const machine = await machineResult.json();
											machines.push(machine);
										}

										context = {domain, customer, site, machines};
									} else {
										context = {domain, customer, site};
									}
								}).catch((err) => alert('Failed to fetch context details!'));
						} else {
							context = {domain, customer};
						}
					}).catch((err) => alert('Failed to fetch context details!'));
			} else {
				context = event.detail;
			}
			
			return;
		}

		if (context.domain && context.domain.uuid) {
			url.searchParams.set('domain', context.domain.uuid);
		}

		if (context.customer && context.customer.uuid) {
			url.searchParams.set('customer', context.customer.uuid);
		} else {
			url.searchParams.delete('customer');
		}

		if (context.site && context.site.uuid) {
			url.searchParams.set('site', context.site.uuid);
		} else {
			url.searchParams.delete('site');
		}

		if (context.machines && (context.machines.length > 0)) {
			let values = null;
			for (let machine of context.machines) {
				values = (values) ? values+'+'+machine.uuid : machine.uuid;
			}

			url.searchParams.set('machines', values);
		} else {
			url.searchParams.delete('machines');
		}
				
		window.history.pushState({path: url.href}, '', url.href);
	};
	
	fetch('/api/v1/domains')
		.then((response) => response.json())
		.then((domain) => refresh({detail: {domain}, root: true}))
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
