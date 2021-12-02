<script>
    import {createEventDispatcher} from 'svelte';
    import Create from 'components/Create.svelte';
    import Item from 'components/Item.svelte';

    export let context;
    export let customer;
    export let onCancel =  null;
    export let onOK = null;
    export let selectCustomer = null;

    const dispatch = createEventDispatcher();
    const icon = 'customer';

    $: domain = context.domain;
    $: item = customer;
    $: select = (selectCustomer) ? selectCustomer : (customer.uuid) ? () => dispatch('refresh', {customer, domain}) : null;
</script>

<Item {icon} {item} {select}>
    <span slot="displayable" style="width: 100%">
        {customer.name}
    </span>
    <span slot="creatable" style="width: 100%">
        {#if onOK}
        <Create {onCancel} {onOK} placeholder1="Enter customer company name"/>
        {/if}
    </span>
</Item>
